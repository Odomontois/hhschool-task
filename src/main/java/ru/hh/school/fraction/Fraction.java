/**
 * Author: Oleg Nizhnik
 * Date  : 12.10.2015
 * Time  : 16:45
 */
package ru.hh.school.fraction;

import ru.hh.school.typeclasses.Whole;
import ru.hh.school.utils.Concatenation;
import ru.hh.school.utils.CyclicSequence;
import ru.hh.school.utils.Unfold;

import java.util.Collections;
import java.util.List;

/**
 * Рациональные числа, представленные как пара  целых чисел: числитель и знаменатель
 *
 * @param <N> целый тип
 */
public class Fraction<N> {
    final private boolean negative;
    final private N numerator, denominator;
    final private Whole<N> whole;

    private Fraction(N numerator, N denominator, boolean negative, Whole<N> whole) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.negative = negative;
        this.whole = whole;
    }

    public static <N> Fraction<N> create(final N numerator, final N denominator, final Whole<N> whole) {
        return create(
                whole.abs(numerator),
                whole.abs(denominator),
                whole.sign(numerator) * whole.sign(denominator) == -1,
                whole);
    }

    private static <N> Fraction<N> create(final N numerator, final N denominator, final boolean negative, final Whole<N> whole) {
        if (denominator == whole.zero()) throw new ArithmeticException("division by zero");
        final N gcd = whole.gcd(numerator, denominator);
        return new Fraction<>(
                whole.quot(numerator, gcd),
                whole.quot(denominator, gcd),
                negative,
                whole);
    }


    public boolean isNegative() {
        return negative;
    }

    /**
     * @return Числитель дроби
     */
    public N getNumerator() {
        return numerator;
    }

    /**
     * @return Знаменатель дроби
     */
    public N getDenominator() {
        return denominator;
    }


    /**
     * Дробная часть числа
     *
     * @param base основание системы исчисления
     * @return последовательность цифр в этом основании
     */
    public Iterable<N> fractionalDigits(N base) {
        final N mod = whole.mod(numerator, denominator);
        return Unfold.of(m -> true, m -> whole.divMod(whole.multiply(m, base), denominator), mod);
    }

    /**
     * Последовательность остатков от деления на основание СИ
     * необходима для вычисления цикла дробного представления
     *
     * @param base основание СИ
     * @return последовательность остатков
     */
    public Iterable<N> fractionalRemainders(N base) {
        return Concatenation.of(
                Collections.singleton(whole.mod(numerator, denominator)),
                Unfold.endo(m -> true, m -> whole.mod(whole.multiply(m, base), denominator), numerator));
    }

    /**
     * Возвращает цикличную репрезентацию дробной части
     *
     * @param base основание СИ
     * @return цикл цифр дробной части в СИ
     */
    public CyclicSequence<N> fractionalCycle(N base, CyclicSequence.Method method) {
        final CyclicSequence<N> remainders = CyclicSequence.create(fractionalRemainders(base), method);

        return CyclicSequence.create(
                fractionalDigits(base),
                remainders.getPrefix().size(),
                remainders.getCycle().size());
    }

    @Override
    public String toString() {
        return (negative ? "-" : "") + numerator + "/" + denominator;
    }

    public String toDigitalString(final N base, CyclicSequence.Method method) {
        if (whole.compare(base, whole.one()) <= 0) throw new ArithmeticException("use base >= 2");

        final CyclicSequence<N> fracSeq = fractionalCycle(base, method);
        final List<N> intPartDigs = whole.digits(floor(), base);
        final List<N> cycleDigs = fracSeq.getCycle();

        final String intPart = intPartDigs.isEmpty() ? "0" : whole.toString(intPartDigs, base);
        final String prefix = whole.toString(fracSeq.getPrefix(), base);
        final String cycle;

        if (cycleDigs.size() == 1 && cycleDigs.get(0) == whole.zero())
            cycle = "";
        else cycle = '(' + whole.toString(cycleDigs, base) + ')';

        final String fractional = prefix + cycle;

        final String fracPart = fractional.isEmpty() ? "" : "," + fractional;
        final String signPart = negative ? "-" : "";

        return signPart + intPart + fracPart;
    }

    public String toDigitalString(final N base) {
        return toDigitalString(base, CyclicSequence.Method.CONSTANT_MEMORY);
    }

    public String toDecimalString() {
        return toDigitalString(whole.fromInt(10));
    }

    /**
     * @return целая часть числа
     */
    public N floor() {
        return whole.quot(numerator, denominator);
    }

    /**
     * @return модуль числа
     */
    public Fraction<N> abs() {
        return Fraction.create(numerator, denominator, false, whole);
    }

    /**
     * @return сумма двух дробей
     */
    public Fraction<N> add(final Fraction<N> other) {
        final N add1 = whole.multiply(this.numerator, other.denominator);
        final N add2 = whole.multiply(this.denominator, other.numerator);
        final boolean signDiffers = !(negative ^ other.negative);

        final N num = signDiffers ? whole.add(add1, add2) : whole.subtract(add1, add2);
        final N signedNum = negative ? whole.negate(num) : num;

        final N den = whole.multiply(this.denominator, other.denominator);

        return Fraction.create(signedNum, den, whole);
    }

    /**
     * @return дробь с тем же модулем, но обратным знаком
     */
    public Fraction<N> negate() {
        return new Fraction<>(numerator, denominator, !negative, whole);
    }

    /**
     * @return разность двух дробей
     */
    public Fraction<N> subtract(Fraction<N> other) {
        return this.add(other.negate());
    }

    /**
     * @return произведение двух дробей
     */
    public Fraction<N> multiply(Fraction<N> other) {
        return Fraction.create(
                whole.multiply(this.numerator, other.numerator),
                whole.multiply(this.denominator, other.denominator),
                this.negative ^ other.negative,
                whole
        );
    }

    /**
     * @return частное двух дробей
     */
    public Fraction<N> divide(Fraction<N> other) {
        return Fraction.create(
                whole.multiply(this.numerator, other.denominator),
                whole.multiply(this.denominator, other.numerator),
                this.negative ^ other.negative,
                whole
        );
    }
}
