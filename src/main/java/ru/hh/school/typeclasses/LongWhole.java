/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 10:46
 */
package ru.hh.school.typeclasses;

public class LongWhole implements Whole<Long> {
    private LongWhole() {
    }

    public static Whole<Long> INSTANCE = new LongWhole();

    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long quot(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long mod(Long x, Long y) {
        return x % y;
    }

    @Override
    public Long fromInt(int x) {
        return (long) x;
    }

    @Override
    public Long abs(Long x) {
        return Math.abs(x);
    }

    @Override
    public int sign(Long x) {
        return x > 0L ? 1 : x == 0L ? 0 : -1;
    }

    @Override
    public int toInt(Long x) {
        return (int) (long) x;
    }

    @Override
    public int compare(Long x, Long y) {
        return x.compareTo(y);
    }

    @Override
    public Long negate(Long x) {
        return -x;
    }

    @Override
    public Long parse(String s) {
        return Long.parseLong(s);
    }
}
