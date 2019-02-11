package weather.calculation

import weather.calculation.WeatherCalculation.outputWeatherInfo

object WeatherApp extends App {
  private val inputCities = Seq(
    ("Sydney", "Australia", "2015-12-23 16:02:12"),
    ("Melbourne", "Australia", "2015-12-25 02:30:55"),
    ("Adelaide", "Australia", "2016-01-04 23:05:37")
  )
  outputWeatherInfo(inputCities)
}
