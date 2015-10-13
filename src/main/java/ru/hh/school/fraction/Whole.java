/**
 * Author: Oleg Nizhnik
 * Date  : 09.10.2015
 * Time  : 16:58
 */
package ru.hh.school.fraction;

import ru.hh.school.utils.Pair;
import ru.hh.school.utils.Unfold;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Интерфейс, отвечающий за операции над целыми числами (Int, Long, BigInteger...)
 * Эквивалент Integral в scala\haskell
 *
 * @param <N>
 */
interface Whole<N> {
    N add(N x, N y);

    N subtract(N x, N y);

    N multiply(N x, N y);

    N quot(N x, N y);

    N mod(N x, N y);

    N fromInt(int x);

    int toInt(N x);

    int compare(N x, N y);

    default N abs(N x) {
        return multiply(x, fromInt(sign(x)));
    }

    default int sign(N x) {
        final int cmp = compare(x, zero());
        if (cmp > 0) return 1;
        else if (cmp == 0) return 0;
        else return -1;
    }

    default Pair<N, N> divMod(N x, N y) {
        final N div = quot(x, y);
        final N rem = mod(x, y);
        return Pair.of(div, rem);
    }

    default N zero() {
        return fromInt(0);
    }

    default N one() {
        return fromInt(1);
    }

    default List<N> digits(N num, N base) {
        final LinkedList<N> result = new LinkedList<>();

        Unfold.of(n -> n != zero(), n -> divMod(n, base).swap(), num).forEach(result::addFirst);

        return result;
    }

    default String toString(Iterable<N> digits, N base) {
        final boolean brackets = compare(base, fromInt(36)) > 0;

        final StringBuilder sb = new StringBuilder();

        for (N dig : digits) {
            if (brackets) {
                sb.append("[");
                sb.append(dig.toString());
                sb.append("]");
            } else {
                sb.append(DIGITS.charAt(toInt(dig)));
            }
        }

        return sb.toString();
    }

    String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
