package weather.calculation

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{LocalDateTime, ZoneId, ZonedDateTime}

import weather.etl.{CityPositionGenerator, WeatherInfo}
import weather.model.{ConditionsAlg, HumidityAlg, PressureAlg, TemperatureAlg}

/**
  * Calculate weather info
  */
object WeatherCalculation{
  // the format of input date and time
  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  private val cities = CityPositionGenerator.cityPositions

  /**
    * get weather info
    *
    * @param cityName city name
    * @param country  country name
    * @param localDT  local date time
    * @return output weather string
    */
  def getWeatherInfo(cityName: String, country: String, localDT: String): Option[WeatherInfo] = {
    try {
      val cts = cities.filter(c => c.country.compareToIgnoreCase(country) == 0 &&
        c.city.compareToIgnoreCase(cityName) == 0)

      if (cts.nonEmpty) {
        val ct = cts.head // Get the first city no matter there is more than 1 same name city in a same country

        val dt = LocalDateTime.parse(localDT, formatter)
        val zoneDT = ZonedDateTime.of(dt, ZoneId.of(ct.timeZone)).format(DateTimeFormatter.ISO_INSTANT)

        val temp = TemperatureAlg.getTemperatureValue(ct.latitude, dt)
        val pressure = PressureAlg.getPressureValue(temp, ct.elevation)
        val humidity = HumidityAlg.getHumidityValue(temp, pressure)
        val weatherInfo = ConditionsAlg.getConditionValue(temp, humidity)

        //      println(ct.city, ct.country,
        //        ct.latitude, ct.longitude, ct.elevation,
        //        zoneDT,
        //        weatherInfo,
        //        temp, pressure, humidity)

        Some(WeatherInfo(ct.city, ct.country,
          ct.latitude, ct.longitude, ct.elevation,
          zoneDT,
          weatherInfo,
          temp, pressure, humidity))
      }
      else {
        println(s"Cannot find the city $cityName in the country $country!")
        None
      }
    } catch {
      case ex: DateTimeParseException =>
        println("Please input correct format of date and time (eg. 2019-02-11 02:02:12).")
        throw ex
    }
  }

  /**
    * Output weather info
    * @param cities input info including city name, country name and local date time
    */
  def outputWeatherInfo(cities: Seq[(String, String, String)]): Unit = {
    println("\nOutput weather info:")

    cities.foreach(ct => {
      val infoOpt = getWeatherInfo(ct._1, ct._2, ct._3)

      if (infoOpt.nonEmpty) {
        val info = infoOpt.get
        println(
          s"""${info.location}|${"%.2f" format info.latitude},${"%.2f" format info.longitude},${"%.0f" format info.elevation}|${info.dt}|${info.weatherInfo}|${"%+.1f" format info.temp}|${"%.1f" format info.pressure}|${"%.0f" format info.humidity}""".stripMargin
        )
      }
    })
  }
}
