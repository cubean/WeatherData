package weather.model

import java.time.LocalDateTime

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
          WeatherInfo(c.city, c.county, c.latitude, c.longitude, c.elevation, c.timeZone, "", temp, 0.0, 0.0)
        }
        )
      }
    }
  }
}
