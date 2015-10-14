/**
 * Author: Oleg Nizhnik
 * Date  : 14.10.2015
 * Time  : 10:23
 */
package ru.hh.school.fraction;

import ru.hh.school.typeclasses.Fractional;
import ru.hh.school.typeclasses.Whole;

public class FractionFractional<N> implements Fractional<Fraction<N>> {
    private Whole<N> whole;

    public static <N> FractionFractional<N> getInstance (Whole<N> whole) {
        return new FractionFractional<>(whole);
    }

    @Override
    public Fraction<N> divide(Fraction<N> x, Fraction<N> y) {
        return x.divide(y);
    }

    @Override
    public Fraction<N> add(Fraction<N> x, Fraction<N> y) {
        return x.add(y);
    }

    @Override
    public Fraction<N> subtract(Fraction<N> x, Fraction<N> y) {
        return x.subtract(y);
    }

    @Override
    public Fraction<N> multiply(Fraction<N> x, Fraction<N> y) {
        return x.multiply(y);
    }

    @Override
    public Fraction<N> fromInt(int x) {
        return Fraction.create(whole.fromInt(x), whole.one(), whole);
    }

    @Override
    public int toInt(Fraction<N> x) {
        return whole.toInt(x.floor());
    }

    @Override
    public Fraction<N> parse(String s) {
        final String[] parts = s.split("/");
        return Fraction.create(whole.parse(parts[0]), whole.parse(parts[1]), whole);
    }

    @Override
    public int sign(Fraction<N> x) {
        if (x.isNegative()) return -1;
        else if (whole.compare(x.getNumerator(), whole.zero()) == 0) return 0;
        else return 1;
    }

    @Override
    public Fraction<N> abs(Fraction<N> x) {
        return x.abs();
    }

    @Override
    public Fraction<N> negate(Fraction<N> x) {
        return x.negate();
    }

    private FractionFractional(Whole<N> whole) {
        this.whole = whole;
    }
}
