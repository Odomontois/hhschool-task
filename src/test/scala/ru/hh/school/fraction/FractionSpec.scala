/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 13:58
 */
package ru.hh.school.fraction
import org.scalacheck.{Gen, Arbitrary}
import org.specs2.{ScalaCheck, Specification}
import Arbitrary._
import Gen._

class FractionSpec extends Specification with ScalaCheck {
  type JL = java.lang.Long
  type BI = java.math.BigInteger
  def is =
    s2"""
  Fractions
    integer representations
      Long ${int[java.lang.Long]}
      BigInteger  ${int[java.math.BigInteger]}

    simple decimals
      Long ${nonCyclic[JL]}
      BigInteger ${nonCyclic[BI]}
  """

  def int[X: Arbitrary](implicit w: Whole[X]) = prop { x: X ⇒
    fraction(x, w.one()).toDecimalString == x.toString
  }

  def nonCyclic[X: Arbitrary](implicit w: Whole[X]) = prop { (x: X, dec: DecPower) ⇒
    val str = w.abs(x).toString
    val sign = if (w.compare(x, w.zero()) < 0) "-" else ""
    val f = fraction(x, w.pow(w.fromInt(10), dec.pow)).toDecimalString

    if (str.length > dec.pow) {
      val intPart = str.substring(0, str.length - dec.pow)
      val fracPart = str.substring(str.length - dec.pow).replaceAll("0+$", "")
      f === (if (fracPart.isEmpty) f"$sign$intPart" else f"$sign$intPart,$fracPart")
    } else if (w.compare(x, w.zero()) == 0) {
      f === "0"
    } else {
      val end = str.replaceAll("0+$", "")
      val zeros = "0" * (dec.pow - str.length)
      f === f"${sign}0,$zeros$end"
    }
  }

  def whole[X](implicit w: Whole[X]) = w
  def fraction[X](n: X, d: X)(implicit w: Whole[X]) = Fraction.create(n, d, w)

  implicit val longWhole = LongWhole.INSTANCE
  implicit val bigIntegerWhole = BigIntegerWhole.INSTANCE
  implicit val bigIntegerArb = Arbitrary(arbitrary[BigInt].map(_.bigInteger))
  implicit val arbLong = Arbitrary(chooseNum(-10000000L, 10000000L).map(x => x: java.lang.Long))

  case class DecPower(pow: Int)
  implicit val arbDecPower = Arbitrary(chooseNum(1, 10).map(DecPower(_)))
}
