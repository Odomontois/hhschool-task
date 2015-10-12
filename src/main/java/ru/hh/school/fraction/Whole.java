/**
 * Author: Oleg Nizhnik
 * Date  : 09.10.2015
 * Time  : 16:58
 */
package ru.hh.school.fraction;

import ru.hh.school.utils.Pair;
import ru.hh.school.utils.Unfold;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

interface Whole<N> {
    N add(N x, N y);

    N subract(N x, N y);

    N multiply(N x, N y);

    N quot(N x, N y);

    N mod(N x, N y);


    N fromInt(int x);

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

        Unfold.<N, N>of(n -> {
            if (n == zero()) return Optional.empty();
            return Optional.of(divMod(n, base));
        }).apply(num).forEach(result::addFirst);

        return result;
    }

}
