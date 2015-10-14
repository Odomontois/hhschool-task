/**
 * Author: Oleg Nizhnik
 * Date  : 14.10.2015
 * Time  : 10:09
 */
package ru.hh.school.typeclasses;

/**
 * Операции сложения, умножения вычитания, задающие кольцо
 * Аналог Num в haskell, Numeric в scala
 * @param <N>
 */
public interface Numeric<N> {
    N add(N x, N y);

    N subtract(N x, N y);

    N multiply(N x, N y);

    N fromInt(int x);

    int toInt(N x);

    N parse(String s);

    int sign(N x);

    default N zero() {
        return fromInt(0);
    }

    default N one() {
        return fromInt(1);
    }

    default N abs(N x) {
        return multiply(x, fromInt(sign(x)));
    }

    default N negate(N x) {
        return multiply(x, fromInt(-1));
    }

    default N pow(N x, int p) {
        if (p == 0) return one();
        final N higher = pow(multiply(x, x), p / 2);
        return p % 2 == 0 ? higher : multiply(x, higher);
    }
}
