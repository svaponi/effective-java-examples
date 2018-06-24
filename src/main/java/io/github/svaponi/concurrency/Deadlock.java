// Perverse test of ObservableSet - bottom of Page 267
package io.github.svaponi.concurrency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Example of deadlock.
 * <p>
 * An observer starts a thread which tries to acquire a lock to the list of observer to remove itself,
 * but the list was locked by the main thread which is looping over it to notify all registered observers.
 */
public class Deadlock {

    static volatile Object lastProcessedElement = null;

    static final long delay = 500; // number of milliseconds certainly bigger than a single cycle, used to detect deadlock

    /*
        each second checks if the last element processed by the program is still the same as last cycle,
        if it is, we are in a deadlock situation
     */
    static Runnable deadlockDetector = () -> {
        try {
            while (true) {
                Object last = lastProcessedElement;
                TimeUnit.MILLISECONDS.sleep(delay);
                if (lastProcessedElement != null && lastProcessedElement.equals(last)) {
                    // throw new IllegalStateException("DEADLOCK!");
                    System.err.println("DEADLOCK!");
                    System.exit(1);
                }
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };

    public static void main(final String[] args) {

        final ObservableSet<Integer> set = new ObservableSetImpl<>();

        new Thread(deadlockDetector).start();

        set.registerObserver((s, e) -> lastProcessedElement = e);

        // Observer that uses a background thread needlessly
        set.registerObserver(new Observer<Integer>() {

            @Override
            public void fireAddEvent(final ObservableSet<Integer> s, final Integer e) {

                System.out.println(e);

                if (e == 3) {

                    /*
                    Creates an Executor that uses a single worker thread operating off an unbounded queue.
                    (Note however that if this single thread terminates due to a failure during execution prior to shutdown,
                    a new one will take its place if needed to execute subsequent tasks.)
                    Tasks are guaranteed to execute sequentially, and no more than one task will be active at any given time.
                     */
                    final ExecutorService executor = Executors.newSingleThreadExecutor();
                    final Observer<Integer> observer = this;
                    try {

                        final Future<?> future = executor.submit(new Runnable() {
                            @Override
                            public void run() {
                                s.unregisterObserver(observer);
                            }
                        });

                        future.get();

                    } catch (final ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex.getCause());
                    } finally {
                        executor.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
    }

    @FunctionalInterface
    public interface Observer<E> {
        /**
         * @param set     set emitting the event
         * @param element element added to the set
         */
        void fireAddEvent(ObservableSet<E> set, E element);
    }

    /**
     * Set wrapper object providing notifications whenever an element is added to the set
     *
     * @param <E>
     */
    public interface ObservableSet<E> {

        void registerObserver(Observer<E> observer);

        boolean unregisterObserver(Observer<E> observer);

        boolean add(E element);
    }

    public static class ObservableSetImpl<E> implements ObservableSet<E> {

        private final Set<E> set = new HashSet<>();
        private final List<Observer<E>> observers = new ArrayList<>();

        @Override
        public void registerObserver(final Observer<E> observer) {
            synchronized (observers) {
                observers.add(observer);
            }
        }

        @Override
        public boolean unregisterObserver(final Observer<E> observer) {
            synchronized (observers) {
                return observers.remove(observer);
            }
        }

        @Override
        public boolean add(final E element) {
            final boolean added = set.add(element);
            if (added) {
                /*
                    Main thread acquire lock here to loop over observers.
                    One observer, when notified, tries to acquire this same lock, but, since is already locked, it waits.
                    DEADLOCK:
                    - the observer thread cannot aquire the lock because is taken from main thread, so it waits
                    - the main thread cannot release the lock because is waiting for the observer to finish
                 */
                synchronized (observers) {
                    for (final Observer<E> observer : observers) {
                        observer.fireAddEvent(this, element);
                    }
                }
            }
            return added;
        }
    }
}
