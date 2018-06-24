package org.effectivejava.examples.chapter11.item79;

import org.effectivejava.examples.chapter10.item67.ForwardingSet;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableSetThreadSafeImpl<E> extends ForwardingSet<E> implements ObservableSet<E> {

    public ObservableSetThreadSafeImpl(final Set<E> set) {
        super(set);
    }

    // Thread-safe observable set with CopyOnWriteArrayList - Page 269

    private final List<Observer<E>> observers = new CopyOnWriteArrayList<>();

    @Override
    public void addObserver(final Observer<E> observer) {
        observers.add(observer);
    }

    @Override
    public boolean removeObserver(final Observer<E> observer) {
        return observers.remove(observer);
    }

    private void notifyElementAdded(final E element) {
        for (final Observer<E> observer : observers) {
            observer.added(this, element);
        }
    }

    @Override
    public boolean add(final E element) {
        final boolean added = super.add(element);
        if (added) {
            notifyElementAdded(element);
        }
        return added;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean result = false;
        for (final E element : c) {
            result |= add(element); // calls notifyElementAdded
        }
        return result;
    }
}
