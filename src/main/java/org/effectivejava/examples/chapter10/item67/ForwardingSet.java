// Reusable forwarding class - Page 84
package org.effectivejava.examples.chapter10.item67;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ForwardingSet<E> implements Set<E> {

    private final Set<E> s;

    public ForwardingSet(final Set<E> s) {
        this.s = s;
    }

    @Override
    public void clear() {
        s.clear();
    }

    @Override
    public boolean contains(final Object o) {
        return s.contains(o);
    }

    @Override
    public boolean isEmpty() {
        return s.isEmpty();
    }

    @Override
    public int size() {
        return s.size();
    }

    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @Override
    public boolean add(final E e) {
        return s.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        return s.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return s.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        return s.addAll(c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return s.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return s.retainAll(c);
    }

    @Override
    public Object[] toArray() {
        return s.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean equals(final Object o) {
        return s.equals(o);
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public String toString() {
        return s.toString();
    }
}
