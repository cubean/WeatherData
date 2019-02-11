package weather.etl

/**
  * City position info including time zone
  * @param city city name
  * @param country country name
  * @param latitude latitude value
  * @param longitude longitude value
  * @param elevation elevation value
  * @param timeZone time zone
  */
case class CityPosition(city: String, country: String,
                        latitude: Double, longitude: Double, elevation: Double,
                        timeZone: String)
