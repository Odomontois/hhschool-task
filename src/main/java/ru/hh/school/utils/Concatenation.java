/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 13:36
 */
package ru.hh.school.utils;

import java.util.Iterator;

/**
 * Конкатенация двух Iterable
 */
public class Concatenation<A> implements Iterable<A> {
    final Iterable<? extends A> first;
    final Iterable<? extends A> second;

    private Concatenation(Iterable<A> first, Iterable<A> second) {
        this.first = first;
        this.second = second;
    }

    public static <A> Concatenation<A> of(Iterable<A> first, Iterable<A> second) {
        return new Concatenation<>(first, second);
    }

    @Override
    public Iterator<A> iterator() {
        return new CIterator();
    }

    private class CIterator implements Iterator<A> {
        boolean switched = false;
        Iterator<? extends A> it = first.iterator();

        private void checkSwitch() {
            if (it.hasNext() || switched) return;
            switched = true;
            it = second.iterator();
        }

        @Override
        public boolean hasNext() {
            checkSwitch();
            return it.hasNext();
        }

        @Override
        public A next() {
            checkSwitch();
            return it.next();
        }
    }
}
