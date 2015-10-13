/**
 * Author: Oleg Nizhnik
 * Date  : 12.10.2015
 * Time  : 16:55
 */
package ru.hh.school.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Аналог Data.List.unfoldr в Haskell
 * Представляет из себя последовательность элементов, генерируемую вызовом бинарной функции
 */
public class Unfold<A, B> implements Iterable<A> {
    final private Function<B, Pair<A, B>> unfold;
    final private B start;
    final private Function<B, Boolean> doWhile;

    protected Unfold(Function<B, Pair<A, B>> unfold, B start, Function<B, Boolean> doWhile) {
        this.unfold = unfold;
        this.start = start;
        this.doWhile = doWhile;
    }

    /**
     * Генерация последовательности на основе промежуточного состояния
     */
    public static <A, B> Unfold<A, B> of(Function<B, Boolean> hasNext, Function<B, Pair<A, B>> unfold, B start) {
        return new Unfold<>(unfold, start, hasNext);
    }

    /**
     * Упрощённый вариант - для функций, генерирующих последовательность элеметов на основе эндоморфизмов
     */
    public static <A> Unfold<A, A> endo(Function<A, Boolean> hasNext, Function<A, A> unfold, A start) {
        return new Unfold<>(unfold.andThen(x -> Pair.of(x, x)), start, hasNext);
    }


    @Override
    public Iterator<A> iterator() {
        return new UnfoldIterator(start);
    }


    private class UnfoldIterator implements Iterator<A> {
        private B state;

        public UnfoldIterator(B state) {
            this.state = state;
        }

        @Override
        public boolean hasNext() {
            return doWhile.apply(state);
        }

        @Override
        public A next() {
            if (!hasNext()) throw new NoSuchElementException();
            final Pair<A, B> result = unfold.apply(state);
            this.state = result.getSecond();
            return result.getFirst();
        }
    }
}

