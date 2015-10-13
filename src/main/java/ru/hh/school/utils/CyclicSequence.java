/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 9:27
 */
package ru.hh.school.utils;

import java.util.*;

public class CyclicSequence<A> {
    final Iterable<A> elements;
    final List<A> prefix;
    final List<A> cycle;

    public enum Method {
        /**
         * Метод, находящий цикл за O(префикс + длина цикла) памяти,
         * с использованием минимального числа итераций
         * в случае, если для элемента определены адекватные equals\hashCode
         */
        MEMOIZE {
            @Override
            <A> Pair<Integer, Integer> findCycle(Iterable<A> elements) {
                final LinkedHashSet<A> seen = new LinkedHashSet<>();
                final Iterator<A> it = elements.iterator();
                A current = it.next();

                while (!seen.contains(current)) {
                    seen.add(current);
                    current = it.next();
                }

                int prefix = 0;
                for (A elem : seen) {
                    if (elem == current) break;
                    prefix += 1;
                }
                return Pair.of(prefix, seen.size() - prefix);
            }
        },

        /**
         * Метод, находящий цикл за O(1) памяти
         * в случае, если итератор использует O(1) памяти
         */
        CONSTANT_MEMORY {
            @Override
            <A> Pair<Integer, Integer> findCycle(Iterable<A> elements) {
                final Iterator<A>
                        slow = elements.iterator(),
                        fast = elements.iterator();
                int meet = 1;
                fast.next();
                while (slow.next() != fast.next()) {
                    meet++;
                    fast.next();
                }

                int prefix = 0;
                final Iterator<A>
                        fromStart = elements.iterator(),
                        skipped = elements.iterator();
                for (int i = 0; i < meet; i++)
                    skipped.next();
                while (fromStart.next() != skipped.next())
                    prefix++;

                int cycle = 1;
                final A current = fromStart.next();
                while (fromStart.next() != current) {
                    cycle++;
                }

                return Pair.of(prefix, cycle);
            }
        };

        abstract <A> Pair<Integer, Integer> findCycle(Iterable<A> elements);
    }

    protected CyclicSequence(Iterable<A> elements, List<A> prefix, List<A> cycle) {
        this.elements = elements;
        this.prefix = prefix;
        this.cycle = cycle;
    }

    public static <A> CyclicSequence<A> create(Iterable<A> elements, Method method) {
        final Pair<Integer, Integer> cycle = method.findCycle(elements);

        return create(elements, cycle.getFirst(), cycle.getSecond());
    }

    public static <A> CyclicSequence<A> create(Iterable<A> elements, int prefixLength, int cycleLength) {
        final int size = prefixLength + cycleLength;
        final List<A> full = new ArrayList<>(size);
        final Iterator<A> it = elements.iterator();
        for (int i = 0; i < size; i++)
            full.add(it.next());

        return new CyclicSequence<>(elements,
                full.subList(0, prefixLength),
                full.subList(prefixLength, size));
    }

    public Iterable<A> getElements() {
        return elements;
    }

    public List<A> getPrefix() {
        return prefix;
    }

    public List<A> getCycle() {
        return cycle;
    }
}
