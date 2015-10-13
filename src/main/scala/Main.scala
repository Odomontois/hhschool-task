import ru.hh.school.mindist.{LabelledPoint, MinDistSearcher, Point}
import scala.collection.JavaConversions._
import scala.util.Random
/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 10:57
 */

object Main {
  import ru.hh.school.fraction._
  import Instances._

  def main(args: Array[String]): Unit = {
    val f = new Fraction[Int](170, 12, whole)

    println(f)
    println(f.toDecimalString)
    println(f.toDigitalString(2))
  }
}
