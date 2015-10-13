/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 10:57
 */

object Main {
  import ru.hh.school.fraction._
  import Instances._

  implicit val longWhole = LongWhole.INSTANCE
  implicit val bigIntegerWhole = BigIntegerWhole.INSTANCE

  def whole[N](implicit w: Whole[N]) = w
  def fraction[N](a: N, b: N)(implicit w: Whole[N]) = Fraction.create(a, b, w)

  def main(args: Array[String]): Unit = {
    //    val f = fraction[java.lang.Long](0L, 1L)
    type JL = java.lang.Long

    val u = 1L to 20L map (x ⇒ fraction[BigInt](whole[BigInt].one,x)) reduce (_ add _)
    //
    println(u)
    println(u.toDecimalString)
    //    println(f.toDigitalString(2L))
    //    println(f.toDigitalString(16L))
    //    println(f.toDigitalString(32L))
    //    println(f.toDigitalString(100L))
  }
}
