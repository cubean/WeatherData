package weather.model

/**
  * Pressure algorithm
  */
object PressureAlg {
  // static pressure (pressure at sea level) [Pa]
  private val Pb: Double = 101325

  // standard temperature lapse rate [K/m] = -0.0065 [K/m]
  private val Lb: Double = -0.0065

  // gravitational acceleration constant = 9.80665
  private val g0 = 9.80665

  // molar mass of Earth’s air = 0.0289644 [kg/mol]
  private val M = 0.0289644

  // universal gas constant = 8.31432
  private val R = 8.31432

  /**
    * Get the pressure value according the temperature and elevation of the city.[hPa]
    *
    * @param temp     the temperature value of the city.[C]
    * @param elevation the altitude value of the city. height about sea level [m].
    * @return Pressure value. [hPa]
    */
  def getPressureValue(temp: Double, elevation: Double): Double = {
    Pb * math.pow(1 - 0.02257 * elevation / 1000, -g0 * M / (R * Lb)) / 100
  }
}
