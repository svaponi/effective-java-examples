// Set obeserver callback interface - Page 266
package org.effectivejava.examples.chapter11.item79;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@FunctionalInterface
public interface Observer<E> {
    // Invoked when an element is added to the observable set
    void added(ObservableSet<E> set, E element);

    Map<Object, AtomicInteger> counters = new HashMap<>();

    Observer printObserver = (s, e) -> {
        Long key = Thread.currentThread().getId();
        counters.computeIfAbsent(key, k -> new AtomicInteger());
        System.out.printf("%10s%s", e, counters.get(key).incrementAndGet() % 10 == 0 ? "\n" : " ");
    };
}
