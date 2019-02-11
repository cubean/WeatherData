package weather.etl

import org.scalatest.{FlatSpec, Matchers}

class ETLTester extends FlatSpec with Matchers {
  "cityLatLngSeq"  should "get back cities with Sydney name" in {
    println(">> cityLatLngSeq test start")

    val cityLatLngSeq: Seq[CityPositionGenerator.cityLatLngInfo] =
      CityPositionGenerator.cityLatLng.filter(c => c.name.compareToIgnoreCase("Sydney") == 0)
    cityLatLngSeq should not be empty
    cityLatLngSeq.foreach(println)

    println(">> cityLatLngSeq test end.")
  }

  "cityElevationTimeZone" should "get back cities with Sydney name" in {
    println(">> cityElevationTimeZone test start")

    val cityElevationTimeZoneSeq=
      CityPositionGenerator.cityElevationTimeZone.
      filter(c => c.name.compareToIgnoreCase("Sydney") == 0)

    cityElevationTimeZoneSeq should not be empty
    cityElevationTimeZoneSeq.foreach(println)

    println(">> cityElevationTimeZone test end.")
  }

  "cityPositions" should "get back cities with Sydney name" in {
    println(">> cityPositions test start")

    val cityPositionsSeq=
      CityPositionGenerator.cityPositions.
        filter(c => c.city.compareToIgnoreCase("Sydney") == 0)

    cityPositionsSeq should not be empty
    cityPositionsSeq.foreach(println)

    println(">> cityPositions test end.")
  }
}
