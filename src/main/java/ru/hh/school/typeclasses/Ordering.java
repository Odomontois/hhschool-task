/**
 * Author: Oleg Nizhnik
 * Date  : 14.10.2015
 * Time  : 9:41
 */
package ru.hh.school.typeclasses;

/**
 * Операция сравнения, далёкий аналог Ord в haskell и Ordering в scala
 */
public interface Ordering<N> {
    int compare(N x, N y);
}
