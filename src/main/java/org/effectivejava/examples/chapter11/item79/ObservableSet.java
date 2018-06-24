package org.effectivejava.examples.chapter11.item79;

import java.util.Set;

public interface ObservableSet<E> extends Set<E> {

    void addObserver(Observer<E> observer);

    boolean removeObserver(Observer<E> observer);
}
