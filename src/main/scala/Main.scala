import ru.hh.school.mindist.{LabelledPoint, MinDistSearcher, Point}
import scala.collection.JavaConversions._
/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 10:57
 */

object Main {
  def main(args: Array[String]): Unit = {
    val points = Seq[(Double,Double)](
      (49.702871008309586, 100.0),
      (1.0, -65.6176681878722),
      (27.43626426511902, -100.0),
      (-1.0, 82.48871182763617),
      (-100.0, 11.98259985851324)

    ) map (new Point(_, _)).tupled

    println(MinDistSearcher.find(points))

    println(points.combinations(2).map {
      case Seq(p1, p2) ⇒ (p1, p2, p1 dist p2)
    } minBy (_._3))
  }
}
