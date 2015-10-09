import ru.hh.school.mindist.{LabelledPoint, MinDistSearcher, Point}
import scala.collection.JavaConversions._
import scala.util.Random
/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 10:57
 */

object Main {
  class Counter {
    var count: Int = 0
  }

  class CountPoint(x: Double, y: Double, counter: Counter) extends Point(x, y) {
    override def dist(point: Point): Double = {
      counter.count += 1
      super.dist(point)
    }
  }

  def main(args: Array[String]): Unit = {
    val counter = new Counter

    val count = 2000000

    def randoms = List.fill(count)(Random.nextGaussian() * 1000)

    val points =
    //      Seq[(Double, Double)](
    //      (49.702871008309586, 100.0),
    //      (1.0, -65.6176681878722),
    //      (27.43626426511902, -100.0),
    //      (-1.0, 82.48871182763617),
    //      (-100.0, 11.98259985851324)
    //
    //    )

      randoms zip randoms map { case (x, y) ⇒ new CountPoint(x, y, counter) }


    println(MinDistSearcher.find(points))

    println(s"distance computed ${counter.count} times")

//    counter.count = 0

//    println(points.combinations(2).map {
//      case Seq(p1, p2) ⇒ (p1, p2, p1 dist p2)
//    } minBy (_._3))

//    println(s"distance computed ${counter.count} times")
  }
}
