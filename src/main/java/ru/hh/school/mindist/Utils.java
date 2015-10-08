/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 17:36
 */
package ru.hh.school.mindist;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Утилиты для обработки списов
 */
public class Utils {
    /**
     * Слияние заранее сортированных коллекций в новую сортированную коллекцию
     */
    public static <E> List<E> merge(List<E> list1, List<E> list2, Comparator<E> comp) {
        LinkedList<E> result = new LinkedList<>();
        PeekIterator<E> it1 = new PeekIterator<>(list1);
        PeekIterator<E> it2 = new PeekIterator<>(list2);

        while (!it1.isEnded() && !it2.isEnded()) {
            if (comp.compare(it1.peek(), it2.peek()) <= 0) {
                result.addLast(it1.pull());
            } else {
                result.addLast(it2.pull());
            }
        }
        it1.forEach(result::addLast);
        it2.forEach(result::addLast);
        return result;
    }

    public static class PeekIterator<E> {
        private E current;
        private boolean ended = false;
        private Iterator<E> backing;

        public void next() {
            if (ended) throw new IllegalStateException();

            if (backing.hasNext()) current = backing.next();
            else ended = true;
        }

        public E pull() {
            E value = peek();
            next();
            return value;
        }

        public E peek() {
            if (ended) throw new IllegalStateException();

            return current;
        }

        public boolean isEnded() {
            return ended;
        }

        public PeekIterator(Iterable<E> collection) {
            backing = collection.iterator();
            next();
        }

        public void forEach(Consumer<E> consumer) {
            if (ended) return;
            consumer.accept(current);
            backing.forEachRemaining(consumer);
        }
    }
}
