package weather.model

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

/**
  * The algorithm of temperature
  */
object TemperatureAlg {
  /**
    * Get Temperature value according latitude and time
    * T = Annual Mean Temp + Annual Revised Temp + Daily Revised Temp
    *
    * @param latitude city latitude value
    * @param dt       LocalDateTime
    * @return temperature value
    */
  def getTemperatureValue(latitude: Double, dt: LocalDateTime): Double = {
    TemperatureAvgAtLatitude.TemperatureAvgCurves.value(latitude) +
      annualRevisedT(latitude, dt.toLocalDate) + dayRevisedT(latitude, dt)
  }

  /**
    * The value revised with year
    * @param latitude city latitude value
    * @param dt LocalDate
    * @return revised annual temperature
    */
  private def annualRevisedT(latitude: Double, dt: LocalDate): Double = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val daysLast1222 = LocalDate.parse((dt.getYear - 1).toString + "-12-22", formatter)

    val sunLatitude = -23.45 * math.cos((dt.toEpochDay - daysLast1222.toEpochDay) * 2 * math.Pi / 365)
    val calPos = if (latitude > 23.45) 23.45 else if (latitude < -23.45) -23.45 else latitude

    // Get the range value of temperature variable
    math.abs(TemperatureAvgAtLatitude.TRange(latitude)) * math.cos((calPos - sunLatitude) * math.Pi / (2 * 23.45))
  }

  /**
    * The value revised with day and night by earth rotation
    * I assumed the maximum variation range of temperature in a day is 10% of the latitude value.
    * @param latitude city latitude value
    * @param dt LocalDateTime
    * @return revised daily temperature
    */
  private def dayRevisedT(latitude: Double, dt: LocalDateTime): Double = {
    val avgT = TemperatureAvgAtLatitude.TemperatureAvgCurves.value(latitude)
//    println(s"avgT is $avgT")
    math.abs(avgT) * 0.1 *
      math.cos((dt.getHour * 3600 + dt.getMinute * 60 + dt.getSecond) *
        2 * math.Pi / (24 * 3600)) * (-1)
  }
}
