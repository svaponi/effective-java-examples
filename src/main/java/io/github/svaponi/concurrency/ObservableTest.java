package io.github.svaponi.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ObservableTest {

    static final AtomicInteger counter = new AtomicInteger();

    public static void main(final String[] args) {
        final Observable<Integer> set = new Observable();
        set.registerObserver((s, e) -> System.out.printf("%3d%s", e, Math.random() > 0.95 ? "\n" : " "));
        for (; counter.getAndIncrement() < 100; ) {
            set.fire(counter.get());
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
