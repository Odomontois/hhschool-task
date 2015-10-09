/**
 * Author: Oleg Nizhnik
 * Date  : 09.10.2015
 * Time  : 12:44
 */
package ru.hh.school.utils;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Итератор, хранящий текущее состояние для просмотра
 */
public class PeekableIterator<E> implements Iterator<E> {
    private final Iterator<E> backing;
    private boolean havePeek = false;
    private E peek;

    public PeekableIterator(Iterator<E> backing) {
        this.backing = backing;
    }

    @Override
    public boolean hasNext() {
        return havePeek || backing.hasNext();
    }

    @Override
    public E next() {
        if (havePeek) {
            havePeek = false;
            return peek;
        } else {
            return backing.next();
        }
    }

    public E peekNext() {
        if (!havePeek) {
            peek = backing.next();
            havePeek = true;
        }
        return peek;
    }
}
