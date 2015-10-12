/**
 * Author: Oleg Nizhnik
 * Date  : 12.10.2015
 * Time  : 17:20
 */
package ru.hh.school.utils;

/**
 * Простая пара значений
 *
 * @param <A>
 * @param <B>
 */
public class Pair<A, B> {
    private final A first;
    private final B second;

    protected Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    Pair<B, A> swap() {
        return of(second, first);
    }
}
