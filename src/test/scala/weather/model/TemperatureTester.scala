package weather.model

import java.time.LocalDate

import org.scalatest.{FlatSpec, PrivateMethodTester}

class TemperatureTester extends FlatSpec with PrivateMethodTester {
  "A latitude" should "returns reasonable temperature at some day of a year" in {
    println("\n>> cityLatLngSeq test start")

    val annualRevisedT: PrivateMethod[Double] = PrivateMethod[Double]('annualRevisedT)

    val latitudePoints: Seq[Double] = Seq(0, 23.45, 45, 90, -23.45, -45, -90)
    val localDTPoints: Seq[LocalDate] =
      Seq(LocalDate.now,
        LocalDate.now.minusMonths(3),
        LocalDate.now.minusMonths(6),
        LocalDate.now.minusMonths(9))

    latitudePoints.flatMap {
      l => {
        localDTPoints.map {
          dt => {
            val tValue = TemperatureAlg invokePrivate annualRevisedT(l, dt)
            val tValueStr = "%+.1f".format(tValue)
            println(s"Annual revised temperature at $l in $dt is $tValueStr")
            val rangeMax = TemperatureAvgAtLatitude.TRange(l)
            assert(math.abs(tValue) <= math.abs(rangeMax))
            (l, dt, tValue, rangeMax)
          }
        }
      }
    }.foreach(p =>
      if (p._1 > 0 && p._2.getMonthValue >= 4 && p._2.getMonthValue <= 8)
        assert(p._3 >= 0, "Temperature of north hemisphere from April to Aug. should be higher than average temperature")
      else if (p._1 < 0 && p._2.getMonthValue >= 4 && p._2.getMonthValue <= 8)
        assert(p._3 <= 0, "Temperature of southern hemisphere from April to Aug. should be lower than average temperature")
      else if (p._1 > 0 && p._2.getMonthValue >= 1 && p._2.getMonthValue <= 2)
        assert(p._3 <= 0, "Summer temperature of north hemisphere from Jan. to Feb. should be lower than average temperature")
      else if (p._1 < 0 && p._2.getMonthValue >= 1 && p._2.getMonthValue <= 2)
        assert(p._3 >= 0, "Summer temperature of southern hemisphere from Jan. to Feb. should be higher than average temperature")
    )
    println(">> TemperatureTester test end.\n")
  }
}
