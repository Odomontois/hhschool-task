/**
 * Author: Oleg Nizhnik
 * Date  : 12.10.2015
 * Time  : 16:45
 */
package ru.hh.school.fraction;

import ru.hh.school.utils.Concatenation;
import ru.hh.school.utils.CyclicSequence;
import ru.hh.school.utils.Pair;
import ru.hh.school.utils.Unfold;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Рациональные числа, представленные как пара  целых чисел: числитель и знаменатель
 *
 * @param <N> целый тип
 */
public class Fraction<N> {
    final private N numerator, denominator;
    final Whole<N> whole;

    public Fraction(N numerator, N denominator, Whole<N> whole) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.whole = whole;
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
     * @return целая часть числа
     */
    public N floor() {
        return whole.quot(numerator, denominator);
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
        return numerator + "/" + denominator;
    }

    public String toDigitalString(N base) {
        final CyclicSequence<N> fracSeq = fractionalCycle(base, CyclicSequence.Method.MEMOIZE);
        final List<N> intPartDigs = whole.digits(floor(), base);
        final List<N> cycleDigs = fracSeq.getCycle();

        final String intPart = intPartDigs.isEmpty() ? "0" : whole.toString(intPartDigs, base);
        final String prefix = whole.toString(fracSeq.getPrefix(), base);
        final String cycle;
        if (cycleDigs.size() == 1 && cycleDigs.get(0) == whole.zero())
            cycle = "";
        else cycle = '(' + whole.toString(cycleDigs, base) + ')';

        return intPart + ',' + prefix + cycle;
    }

    public String toDecimalString() {
        return toDigitalString(whole.fromInt(10));
    }
}
