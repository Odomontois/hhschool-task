/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 12:16
 */
package ru.hh.school.fraction
import java.util

import ru.hh.school.utils.Pair

object Instances {
  import Integral.Implicits._
  implicit def tuple2toPair[A, B] = (Pair.of[A, B] _).tupled

  implicit def integral2Whole[N: Integral] = new Whole[N] {
    def int = implicitly[Integral[N]]
    override def add(x: N, y: N): N = x + y
    override def multiply(x: N, y: N): N = x * y
    override def toInt(x: N): Int = x.toInt()
    override def quot(x: N, y: N): N = x / y
    override def subtract(x: N, y: N): N = x - y
    override def fromInt(x: Int): N = int.fromInt(x)
    override def mod(x: N, y: N): N = x % y
    override def abs(x: N): N = int.abs(x)
    override def sign(x: N): Int = int.signum(x)
    override def zero(): N = int.zero
    override def one(): N = int.one
    override def compare(x: N, y: N): Int = int.compare(x, y)
    override def divMod(x: N, y: N): Pair[N, N] = x /% y
  }
}
