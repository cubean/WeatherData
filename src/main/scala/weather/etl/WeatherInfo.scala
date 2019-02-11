package weather.etl

/**
  * Weather information class
  *
  * @param location    City name.
  * @param country     Country name.
  * @param latitude    Decimal degrees, usually to six significant digits. Negative is South, positive is North.
  * @param longitude   Decimal degrees, usually to six significant digits. Negative is West, positive is East.
  * @param elevation   Elevation value [metre].
  * @param dt          ISO date time string.
  * @param weatherInfo weather information string [Sunny, Rain or Snow]
  * @param temp        the temperature value of the city.[C].
  * @param pressure    the pressure value of the city. [hPa](100 Pa)
  * @param humidity    the humidity value of the city. [%]
  *
  */
case class WeatherInfo(location: String,
                       country: String,
                       latitude: Double, longitude: Double, elevation: Double,
                       dt: String,
                       weatherInfo: String,
                       temp: Double,
                       pressure: Double,
                       humidity: Double)
