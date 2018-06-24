package org.effectivejava.examples.chapter11.item79;

import org.effectivejava.examples.chapter10.item67.ForwardingSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ObservableSetImpl<E> extends ForwardingSet<E> implements ObservableSet<E> {

    public ObservableSetImpl(final Set<E> set) {
        super(set);
    }

    private final List<Observer<E>> observers = new ArrayList<>();

    @Override
    public void addObserver(final Observer<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public boolean removeObserver(final Observer<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    // This method is the culprit
    private void notifyElementAdded(final E element) {
        synchronized (observers) {
            for (final Observer<E> observer : observers) {
                observer.added(this, element);
            }
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
