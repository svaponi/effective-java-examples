// More complex test of ObservableSet - Page 267
package org.effectivejava.examples.chapter11.item79;

import java.util.HashSet;

public class Test2 {

    public static void main(final String[] args) {

        final ObservableSet<Integer> set = new ObservableSetImpl<>(new HashSet<>());

        set.addObserver(Observer.printObserver);

        set.addObserver(new Observer<Integer>() {
            @Override
            public void added(final ObservableSet<Integer> s, final Integer e) {
                if (e == 3) {
                    s.removeObserver(this);
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
