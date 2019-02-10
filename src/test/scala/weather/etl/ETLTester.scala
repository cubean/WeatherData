package weather.etl

import org.scalatest.FunSuite

class ETLTester extends FunSuite{
  test("cityLatLngSeq -  should get back cities with Sydney name") {
    println(">> cityLatLngSeq test start")

    val cityLatLngSeq= CityPositionGenerator.cityLatLng.filter(c => c.name.compareToIgnoreCase("Sydney") == 0)
    assert(cityLatLngSeq.nonEmpty)
    cityLatLngSeq.foreach(println)

    println(">> cityLatLngSeq test end.")
  }

  test("cityElevationTimeZone -  should get back cities with Sydney name") {
    println(">> cityElevationTimeZone test start")

    val cityElevationTimeZoneSeq=
      CityPositionGenerator.cityElevationTimeZone.
      filter(c => c.name.compareToIgnoreCase("Sydney") == 0)

    assert(cityElevationTimeZoneSeq.nonEmpty)
    cityElevationTimeZoneSeq.foreach(println)

    println(">> cityElevationTimeZone test end.")
  }

  test("cityPositions -  should get back cities with Sydney name") {
    println(">> cityPositions test start")

    val cityPositionsSeq=
      CityPositionGenerator.cityPositions.
        filter(c => c.city.compareToIgnoreCase("Sydney") == 0)

    assert(cityPositionsSeq.nonEmpty)
    cityPositionsSeq.foreach(println)

    println(">> cityPositions test end.")
  }
}
