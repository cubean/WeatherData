package weather.model

import java.time.LocalDateTime

import weather.calculation.WeatherCalculation
import weather.etl.{CityPosition, CityPositionGenerator, WeatherInfo}

/**
  * Supply testing samples of city info
  */
object CitySamples {
  /**
    * City position samples
    */
  lazy val cityPosSamples: Seq[CityPosition] = {
    CityPositionGenerator.cityPositions.filter(c =>
      c.city.compareToIgnoreCase("Sydney") == 0 ||
        c.city.compareToIgnoreCase("Melbourne") == 0 ||
        c.city.compareToIgnoreCase("Adelaide") == 0
    )
  }

  lazy val cityPosInAuSamples: Seq[CityPosition] = {
    cityPosSamples.filter(_.country.compareToIgnoreCase("Australia") == 0)
  }

  private val dtNow = LocalDateTime.now
  private val localDTPoints: Seq[LocalDateTime] =
    Seq(dtNow,
      dtNow.minusMonths(Math.random() * 12 toInt).minusHours(Math.random() * 24 toInt),
      dtNow.minusMonths(Math.random() * 12 toInt).minusHours(Math.random() * 24 toInt),
      dtNow.minusMonths(Math.random() * 12 toInt).minusHours(Math.random() * 24 toInt))

  lazy val cityTempSamples: Seq[WeatherInfo] = {
    cityPosSamples.flatMap {
      c => {
        localDTPoints.map(dt => {
          val temp = TemperatureAlg.getTemperatureValue(c.latitude, dt)
          WeatherInfo(c.city, c.country, c.latitude, c.longitude, c.elevation, c.timeZone, "", temp, 0.0, 0.0)
        }
        )
      }
    }
  }

  lazy val cityPressureSamples: Seq[WeatherInfo] = {
    cityTempSamples.map {
      c => {
        val pressure = PressureAlg.getPressureValue(c.temp, c.elevation)
        WeatherInfo(c.location, c.country, c.latitude, c.longitude, c.elevation, c.dt, c.weatherInfo, c.temp, pressure, c.humidity)
      }
    }
  }

  lazy val cityHumiditySamples: Seq[WeatherInfo] = {
    cityPressureSamples.map {
      c => {
        val humidity = HumidityAlg.getHumidityValue(c.temp, c.pressure)
        WeatherInfo(c.location, c.country, c.latitude, c.longitude, c.elevation, c.dt, c.weatherInfo, c.temp, c.pressure, humidity)
      }
    }
  }

  private val inputCities = Seq(
    ("Sydney", "Australia", "2015-12-23 16:02:12"),
    ("Melbourne", "Australia", "2015-12-25 02:30:55"),
    ("Adelaide", "Australia", "2016-01-04 23:05:37")
  )

  lazy val cityWeatherSamples: Seq[WeatherInfo] = inputCities.flatMap {
    c => WeatherCalculation.getWeatherInfo(c._1, c._2, c._3)
  }
}
