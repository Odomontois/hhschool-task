/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 13:29
 */
package ru.hh.school.mindist

import org.scalacheck.{Gen, Prop, Arbitrary}
import Prop.{falsified, proved}
import org.specs2.matcher.Expectable

import collection.JavaConversions._
import org.specs2.{ScalaCheck, Specification}
import Arbitrary._
import ru.hh.school.util.JavaConversions._

import scala.language.postfixOps

class MinDistSearcherSpec extends Specification with ScalaCheck {

  def is = s2"""
    Minimal distance search
      should handle empty collections $empty
      should handle singleton collections $single
      should always contain optimal points $contains
      should be equal to n^2 bruteforce solution $bruteforce
  """

  def empty = {
    val (dist, p1, p2) = minDist(Seq.empty[Point])
    p1.isEmpty && p2.isEmpty && dist.isPosInfinity
  }

  def single = prop { p: Point ⇒
    val (dist, p1, p2) = minDist(Seq(p))
    p1.fold(falsified)(_ === p) && p2.isEmpty && dist.isPosInfinity
  }

  def bruteforce = prop { (p1: Point, p2: Point, rest: Seq[Point]) ⇒
    val whole = p1 +: p2 +: rest
    val algDist = minDist(whole)._1
    val bruteDist = whole combinations 2 map {
      case Seq(pu, pv) ⇒ pu dist pv
    } min

    algDist === bruteDist
  }

  def contains = prop { points: Seq[Point] ⇒
    val (_, p1, p2) = minDist(points)

    p1.fold(proved)(points.contains(_)) &&
      p2.fold(proved)(points.contains(_))
  }

  implicit val arbPoint = Arbitrary(for {
    x ← Gen.chooseNum(-100.0, 100.0)
    y ← Gen.chooseNum(-100.0, 100.0)
  } yield new Point(x, y))

  def minDist[P <: Point](points: Seq[P]): (Double, Option[P], Option[P]) = {
    val result = MinDistSearcher find points
    (result.getDistance, result.getPoint1.asScala, result.getPoint2.asScala)
  }
}
