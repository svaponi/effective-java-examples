package io.github.svaponi.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class ReentrantLockTest {

    static final ReentrantLock lock = new ReentrantLock(true);
    static final AtomicInteger counter = new AtomicInteger();

    public static void main(final String[] args) {
        new LockInterval((x) -> x).start();
        new LockInterval((x) -> x).start();
        new LockInterval((x) -> x).start();
        new LockInterval((x) -> x).start();
    }

    static void delay(final long delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (final InterruptedException e) {
        }
    }

    private static class LockInterval extends Thread {

        private static final AtomicInteger instanceId = new AtomicInteger();
        private final Function inner;

        public LockInterval(final Function inner) {
            this.inner = inner;
            this.setName("X-" + instanceId.getAndIncrement());
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("" + getName() + " >> lock");
                lock.lock();
                System.out.println("run >> " + getName() + " = " + inner.apply(counter.getAndIncrement()));
                delay((long) (1000 * Math.random()));
                lock.unlock();
            }
        }
    }

    @FunctionalInterface
    public interface Observer<E> {
        /**
         * @param observable source of the event
         * @param event      event fired
         */
        void fireAddEvent(Observable<E> observable, E event);
    }

    public static class Observable<E> {

        private final List<Observer<E>> observers = new ArrayList<>();

        public void registerObserver(final Observer<E> observer) {
            synchronized (observers) {
                observers.add(observer);
            }
        }

        public boolean unregisterObserver(final Observer<E> observer) {
            synchronized (observers) {
                return observers.remove(observer);
            }
        }

        public void fire(final E event) {
            synchronized (observers) {
                for (final Observer<E> observer : observers) {
                    observer.fireAddEvent(this, event);
                }
            }
        }
    }
}
