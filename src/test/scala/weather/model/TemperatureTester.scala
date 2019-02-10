package weather.model

import java.time.{LocalDate, LocalDateTime}

import org.scalatest.{FlatSpec, PrivateMethodTester}

class TemperatureTester extends FlatSpec with PrivateMethodTester {
  "A latitude" should "returns reasonable temperature at some day of a year" in {
    println("\n>> annualRevisedT test start")

    val annualRevisedT: PrivateMethod[Double] = PrivateMethod[Double]('annualRevisedT)

    val latitudePoints: Seq[Double] = Seq(0, 23.45, 45, 90, -23.45, -45, -90)
    val dtNow = LocalDate.now
    val localDTPoints: Seq[LocalDate] =
      Seq(dtNow,
        dtNow.minusMonths(3),
        dtNow.minusMonths(6),
        dtNow.minusMonths(9))

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
    println(">> annualRevisedT test end.\n")
  }

  "A latitude" should "returns reasonable temperature at some time of a day" in {
    println("\n>> dayRevisedT test start")

    val latitudePoints: Seq[Double] = Seq(0, 23.45, 45, 90, -23.45, -45, -90)
    val dtNow = LocalDateTime.now
    val localDTPoints: Seq[LocalDateTime] =
      Seq(dtNow,
        dtNow.minusHours(3),
        dtNow.minusHours(10),
        dtNow.minusHours(20))

    val dayRevisedT: PrivateMethod[Double] = PrivateMethod[Double]('dayRevisedT)

    latitudePoints.foreach {
      l => {
        localDTPoints.foreach {
          dt => {
            val tValue = TemperatureAlg invokePrivate dayRevisedT(l, dt)

            val tValueStr = "%+.1f".format(tValue)
            println(s"Daily revised temperature at $l in $dt is $tValueStr")
            val rangeMax = TemperatureAvgAtLatitude.TRange(l)
            assert(math.abs(tValue) <= math.abs(rangeMax))
          }
        }
      }
    }
    println(">> dayRevisedT test end.\n")
  }

  "A latitude" should "returns reasonable temperature at any time of any year" in {
    println("\n>> getTemperatureValue test start")

    val latitudePoints: Seq[Double] = Seq(0, 23.45, 45, 90, -23.45, -45, -90)
    val dtNow = LocalDateTime.now
    val localDTPoints: Seq[LocalDateTime] =
      Seq(dtNow,
        dtNow.minusMonths(Math.random() * 12 toInt).minusHours(Math.random() * 24 toInt),
        dtNow.minusMonths(Math.random() * 12 toInt).minusHours(Math.random() * 24 toInt),
        dtNow.minusMonths(Math.random() * 12 toInt).minusHours(Math.random() * 24 toInt))

    latitudePoints.foreach {
      l => {
        localDTPoints.foreach {
          dt => {
            val tValue = TemperatureAlg.getTemperatureValue(l, dt)

            val tValueStr = "%+.1f".format(tValue)
            println(s"Revised temperature at $l in $dt is $tValueStr")
            val rangeMax = TemperatureAvgAtLatitude.TRange(l)
            assert(math.abs(tValue) - math.abs(TemperatureAvgAtLatitude.TemperatureAvgCurves.value(l))
              <= (2 * math.abs(rangeMax)))
          }
        }
      }
    }

    println(">> getTemperatureValue test end.\n")
  }

}
