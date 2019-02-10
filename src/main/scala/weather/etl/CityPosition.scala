package weather.etl

case class CityPosition(city: String, county: String,
                        latitude: Double, longitude: Double, altitude: Double,
                        timeZone: String)
