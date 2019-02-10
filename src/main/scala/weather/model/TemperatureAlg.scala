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
    annualRevisedT(latitude, dt.toLocalDate)
  }

  private def annualRevisedT(latitude: Double, dt: LocalDate): Double = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val daysLast1222 = LocalDate.parse((dt.getYear - 1).toString + "-12-22", formatter)

    val sunLatitude = -23.45 * math.cos((dt.toEpochDay - daysLast1222.toEpochDay) * 2 * math.Pi / 365)
    val calPos = if (latitude > 23.45) 23.45 else if (latitude < -23.45) -23.45 else latitude

    // Get the range value of temperature variable
    math.abs(TemperatureAvgAtLatitude.TRange(latitude)) * math.cos((calPos - sunLatitude) * math.Pi / (2 * 23.45))
  }
}
