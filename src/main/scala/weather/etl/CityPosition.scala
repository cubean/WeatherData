package weather.etl

/**
  * City position info including time zone
  * @param city city name
  * @param county country name
  * @param latitude latitude value
  * @param longitude longitude value
  * @param elevation elevation value
  * @param timeZone time zone
  */
case class CityPosition(city: String, county: String,
                        latitude: Double, longitude: Double, elevation: Double,
                        timeZone: String)
