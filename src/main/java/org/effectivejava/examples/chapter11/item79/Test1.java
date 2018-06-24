// Simple test of ObservableSet - Page 266
package org.effectivejava.examples.chapter11.item79;

import java.util.HashSet;

public class Test1 {

    public static void main(final String[] args) {

        final ObservableSetImpl<Integer> set = new ObservableSetImpl<>(new HashSet<>());

        set.addObserver(Observer.printObserver);

//        set.addObserver((s, e) -> {
//            System.out.printf("%3d%s", e, Math.random() > 0.95 ? "\n" : " ");
//        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
