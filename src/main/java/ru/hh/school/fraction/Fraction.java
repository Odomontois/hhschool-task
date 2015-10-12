/**
 * Author: Oleg Nizhnik
 * Date  : 12.10.2015
 * Time  : 16:45
 */
package ru.hh.school.fraction;

import ru.hh.school.utils.Pair;
import ru.hh.school.utils.Unfold;

import java.util.Optional;

public class Fraction<N> {
    final private N numerator, denominator;
    final Whole<N> whole;

    public Fraction(N numerator, N denominator, Whole<N> whole) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.whole = whole;
    }

    public N getNumerator() {
        return numerator;
    }

    public N getDenominator() {
        return denominator;
    }

    public N floor() {
        return whole.quot(numerator, denominator);
    }

    public Iterable<N> fractionalDigits(N base) {
        final N mod = whole.mod(numerator, denominator);
        final Unfold<N, N> unfold = Unfold.of(m -> {
            if (m == whole.zero()) return Optional.empty();
            final N prod = whole.multiply(m, base);
            final N digit = whole.quot(prod, denominator);
            final N next = whole.mod(prod, denominator);

            return Optional.of(Pair.of(digit, next));
        });

        return unfold.apply(mod);
    }

}
