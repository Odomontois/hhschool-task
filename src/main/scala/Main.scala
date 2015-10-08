import ru.hh.school.mindist.{MinDistSearcher, Point}
import scala.collection.JavaConversions._
/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 10:57
 */

object Main {
  def main(args: Array[String]): Unit = {
    val points = Seq((10.0, 10.0), (20.0, 10.0), (20.0, 15.0)) map (new Point(_, _)).tupled

    println(MinDistSearcher.find(points))
  }
}
