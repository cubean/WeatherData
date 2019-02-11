package weather.etl

import scala.io.Source

/**
  * Generate City Position containing latitude, longitude and elevation in metres above sea level
  */
object CityPositionGenerator {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Data Extracting

  /**
    * city latitude longitude info
    * @param name city name
    * @param country country name
    * @param latitude latitude value
    * @param longitude longitude value
    */
  case class cityLatLngInfo(name: String, country: String, latitude: Double, longitude: Double)

  /**
    * Get basic city information - Location, latitude, longitude
    */
  lazy val cityLatLng: Seq[cityLatLngInfo] = {
    println("Loading csv files to get city info...")
    val source = Source.fromResource("worldcities.csv")
    val lineIterator = source.getLines().mkString("\n").split("\n")
    source.close()
    println(s"Line numbers in the file: ${lineIterator.size}")

    lineIterator.tail.map(
      l => {
        val lineInfo = l.replaceAll("\"", "").split(",")
//        println(lineInfo(1), lineInfo(4), lineInfo(2).toDouble, lineInfo(3).toDouble)
        cityLatLngInfo(lineInfo(1), lineInfo(4), lineInfo(2).toDouble, lineInfo(3).toDouble)
      }
    )
  }

  /**
    * city elevation timeZone info
    * @param name city name
    * @param country country name
    * @param elevation elevation value [m]. Need to be converted from feet.
    * @param timeZone timeZone value
    */
  case class cityEleTZInfo(name: String, country: String, elevation: Double, timeZone: String)

  /**
    * Get more city information - elevation, timezone
    */
  lazy val cityElevationTimeZone: Seq[cityEleTZInfo] = {
    println("Loading dat files to get city elevation and timezone info...")
    val source = Source.fromResource("airports.dat")
    val lineIterator = source.getLines().mkString("\n").split("\n")
    source.close()
    println(s"Line numbers in the file: ${lineIterator.size}")

    lineIterator.map(
      l => {
        val lineInfo = l.replaceAll("\"", "").split(",")
        cityEleTZInfo(lineInfo(2), lineInfo(3), lineInfo(8).toDouble * 0.3048, lineInfo(11))
      }
    )
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Data Aggregation

  /**
    * Aggregate 2 parts data with same city name and country name
    */
  lazy val cityPositions: Seq[CityPosition] = cityLatLng.flatMap {
    a => {
      val info2 = cityElevationTimeZone.filter(b =>
        b.name.compareToIgnoreCase(a.name) == 0 && b.country.compareToIgnoreCase(a.country) == 0)

      if (info2.nonEmpty) {
        val headPos = info2.head
        Some(CityPosition(a.name, a.country, a.latitude, a.longitude, headPos.elevation, headPos.timeZone))
      }
      else {
        // Remove the city which the city name or country name is not appeared in the dat file
        None
      }
    }
  }
}

