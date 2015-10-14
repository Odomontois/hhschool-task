/**
 * Author: Oleg Nizhnik
 * Date  : 14.10.2015
 * Time  : 10:21
 */
package ru.hh.school.typeclasses;

public interface Fractional<N> extends Numeric<N> {
    N divide(N x, N y);
}
