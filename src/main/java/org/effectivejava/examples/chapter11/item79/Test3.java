// Perverse test of ObservableSet - bottom of Page 267
package org.effectivejava.examples.chapter11.item79;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test3 {

    public static void main(final String[] args) {

        final ObservableSet<Integer> set = new ObservableSetImpl<>(new HashSet<>());

        set.addObserver(Observer.printObserver);

        // Observer that uses a background thread needlessly
        set.addObserver(new Observer<Integer>() {
            @Override
            public void added(final ObservableSet<Integer> s, final Integer e) {
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
                                s.removeObserver(observer);
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

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
