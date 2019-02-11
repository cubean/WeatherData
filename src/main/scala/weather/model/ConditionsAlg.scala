package weather.model

/**
  * Condition Algorithm
  */
object ConditionsAlg {
  /**
    * Get the weather condition according the temperature and humidity of the city.
    *
    * @param temp the temperature value of the city.[C].
    * @param humidity the humidity value of the city. [%]
    *
    * @return Sunny, Rain or Snow
    */
  def getConditionValue(temp: Double, humidity: Double): String = {
    if (humidity >= 100) {
      if (temp > 0) "Rain" else "Snow"
    } else "Sunny"
  }
}
