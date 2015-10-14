/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 15:09
 */
package ru.hh.school.util

object JavaConversions {
  implicit class optional2scala[T](opt: java.util.Optional[T]) {
    def asScala: Option[T] = if (opt.isPresent) Some(opt.get()) else None
  }
}
