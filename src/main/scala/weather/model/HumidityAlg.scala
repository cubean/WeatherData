package weather.model

/**
  * Humidity Algorithm
  */
object HumidityAlg {
  /**
    * Get humidity value according the temperature and pressure of the city.[%]
    * humidity = (pressure / 10) / vaporPressureCurves.value(temp)
    * Changed kPa to hPa
    *
    * @param temp the temperature value of the city.[C].
    * @param pressure the pressure value of the city. [hPa]
    * @return Humidity value. [hPa]
    */
  def HumidityValue(temp: Double, pressure: Double): Double = {
    val hum = pressure / (vaporPressureCurves.value(temp) * 10)

    if (hum > 100) 100 else hum
  }

  /**
    * Get the pressure value by the polynomial curve
    */
  private val vaporPressureCurves = {

    val valTempDownZero = (-30 until 0 by 5).map(_.toDouble)
    val valTempUpZero = (0 to 100 by 10).map(_.toDouble)

    val valTemp = valTempDownZero ++ valTempUpZero

    // The vapor pressure of water, or saturation vapor pressure,
    // increases strongly with increasing temperature:(kPa)
    val pressureVapor: Seq[Double] = Seq(
      0.049, 0.081, 0.126, 0.191, 0.287, 0.422, 0.61,
      1.23, 2.34, 4.24, 7.37, 12.33, 19.92, 31.18, 47.34, 70.11, 101.33)

    TrainPolynomialCurve.polynomialCurves(valTemp zip pressureVapor)
  }
}
