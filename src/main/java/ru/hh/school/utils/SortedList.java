/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 17:36
 */
package ru.hh.school.utils;

import java.util.*;

/**
 * Двусвязный список с поддержикой операции слияния
 * производящую из двух сортированых списков новый
 */
public class SortedList<E> extends LinkedList<E> {
    /**
     * Слияние заранее сортированных коллекций в новую сортированную коллекцию
     * за O(M+N)
     */
    public SortedList<E> merge(SortedList<E> list2, Comparator<E> comp) {
        SortedList<E> result = new SortedList<>();
        PeekableIterator<E> it1 = new PeekableIterator<>(this.iterator());
        PeekableIterator<E> it2 = new PeekableIterator<>(list2.iterator());

        while (it1.hasNext() && it2.hasNext()) {
            if (comp.compare(it1.peekNext(), it2.peekNext()) <= 0) {
                result.addLast(it1.next());
            } else result.addLast(it2.next());
        }

        it1.forEachRemaining(result::addLast);
        it2.forEachRemaining(result::addLast);
        return result;
    }

    public static <E> SortedList<E> singleton(E e) {
        final SortedList<E> result = new SortedList<>();
        result.add(e);
        return result;
    }

    public static <E extends Comparable<E>> SortedList<E> from(Collection<E> es) {
        return new SortedList<>(es, Comparator.<E>naturalOrder());
    }

    public SortedList(Collection<? extends E> collection, Comparator<E> comparator) {
        super(collection);
        this.sort(comparator);
    }

    public SortedList() {
        super();
    }


}
