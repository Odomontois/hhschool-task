/**
 * Author: Oleg Nizhnik
 * Date  : 12.10.2015
 * Time  : 16:55
 */
package ru.hh.school.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

/**
 * Аналог Data.List.unfold
 * Представляет из себя последовательность вызова некоторой функции
 */
public class Unfold<A, B> implements Function<B, Iterable<A>> {
    final private Function<B, Optional<Pair<A, B>>> unfold;

    protected Unfold(Function<B, Optional<Pair<A, B>>> unfold) {
        this.unfold = unfold;
    }

    public static <A, B> Unfold<A, B> of(Function<B, Optional<Pair<A, B>>> unfold) {
        return new Unfold<A, B>(unfold);
    }

    @Override
    public Iterable<A> apply(B start) {
        return new Unfolder(start);
    }


    private class Unfolder implements Iterable<A> {
        final private B start;

        public Unfolder(B start) {
            this.start = start;
        }

        @Override
        public Iterator<A> iterator() {
            return new UnfoldIterator(start);
        }
    }

    private class UnfoldIterator implements Iterator<A> {
        private B state;
        private Optional<Pair<A, B>> produced = null;

        public UnfoldIterator(B state) {
            this.state = state;
        }

        @Override
        public boolean hasNext() {
            if (produced == null) produced = unfold.apply(state);
            return produced.isPresent();
        }

        @Override
        public A next() {
            if (!hasNext()) throw new NoSuchElementException();
            final Pair<A, B> result = produced.get();
            this.state = result.getSecond();
            return result.getFirst();
        }
    }
}
