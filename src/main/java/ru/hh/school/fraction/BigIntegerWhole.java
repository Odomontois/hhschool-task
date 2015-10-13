/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 10:48
 */
package ru.hh.school.fraction;

import ru.hh.school.utils.Pair;

import java.math.BigInteger;

public class BigIntegerWhole implements Whole<BigInteger> {
    private BigIntegerWhole() {
    }

    public static Whole<BigInteger> INSTANCE = new BigIntegerWhole();

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger quot(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        return x.mod(y);
    }

    @Override
    public BigInteger fromInt(int x) {
        return BigInteger.valueOf(x);
    }

    @Override
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger one() {
        return BigInteger.ONE;
    }

    @Override
    public Pair<BigInteger, BigInteger> divMod(BigInteger x, BigInteger y) {
        final BigInteger[] divmod = x.divideAndRemainder(y);
        return Pair.of(divmod[0], divmod[1]);
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }

    @Override
    public int sign(BigInteger x) {
        return x.signum();
    }

    @Override
    public int toInt(BigInteger x) {
        return x.intValue();
    }

    @Override
    public int compare(BigInteger x, BigInteger y) {
        return x.compareTo(y);
    }
}
