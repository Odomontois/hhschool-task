/**
 * Author: Oleg Nizhnik
 * Date  : 09.10.2015
 * Time  : 13:32
 */
package ru.hh.school.util
import java.util.Comparator

import org.scalacheck.Arbitrary

import org.scalacheck.Gen
import org.specs2.{ScalaCheck, Specification}
import ru.hh.school.utils.SortedList
import collection.JavaConversions._
import scala.language.higherKinds

class SortedListSpec extends Specification with ScalaCheck {
  import Arbitrary._
  import Gen._
  def is =
    s2"""
        SortedList should
          merge lists $merge
      """
  implicit def arbNonEmpty[E: Arbitrary] = Arbitrary(nonEmptyListOf(arbitrary[E]).map(new NonEmpty[List, E](_)))

  def merge = prop { (l1: NonEmpty[List, Int], l2: NonEmpty[List, Int]) ⇒
    val asInteger: Int ⇒ Integer = x ⇒ x

    val l1s = l1.coll.sorted map asInteger
    val l2s = l2.coll.sorted map asInteger

    val merged = SortedList from l1s merge(SortedList from l2s, Comparator.naturalOrder[Integer]) toList
    val sorted = (l1.coll ++ l2.coll).sorted map asInteger
    merged === sorted
  }
}

class NonEmpty[T[X], E](val coll: T[E]) extends AnyVal
