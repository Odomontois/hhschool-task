/**
 * Author: Oleg Nizhnik
 * Date  : 09.10.2015
 * Time  : 16:58
 */
package ru.hh.school.typeclasses;

import ru.hh.school.utils.Pair;
import ru.hh.school.utils.Unfold;

import java.util.LinkedList;
import java.util.List;

/**
 * Интерфейс, отвечающий за операции над целыми числами (Int, Long, BigInteger...)
 * Эквивалент Integral в scala\haskell
 *
 * @param <N>
 */
public interface Whole<N> extends Numeric<N>, Ordering<N> {
    N quot(N x, N y);

    N mod(N x, N y);


    default int sign(N x) {
        final int cmp = compare(x, zero());
        if (cmp > 0) return 1;
        else if (cmp == 0) return 0;
        else return -1;
    }

    /**
     * Имплементация по умолчанию методом Эйлера
     * @return Наибольший общий делитель x и y
     */
    default N gcd(N x, N y) {
        if (compare(x, y) > 0) return gcd(y, x);
        if (compare(x, zero()) == 0) return y;
        return gcd(mod(y, x), x);
    }

    default Pair<N, N> divMod(N x, N y) {
        final N div = quot(x, y);
        final N rem = mod(x, y);
        return Pair.of(div, rem);
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
