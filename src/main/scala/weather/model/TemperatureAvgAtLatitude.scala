package weather.model

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction

/**
  * Temperature average value curve and max variable value curve
  * Curve fitting with polynomial curve with 3 degrees
  */
object TemperatureAvgAtLatitude {
  /**
    * Fitting points with latitude
    */
  private val valLatitude = (85 to -85 by -10).map(_.toDouble)

  /**
    * Get an average temperature value with latitude value by polynomial curves
    */
  private val TemperatureAvgCurves: PolynomialFunction = {

    // The data contains some actual measurements
    // from S.G. Warren & S.T. Schneider, J. Atmos. Sci. 36, 1377-91 (1979)
    val WeightedObservedPointsTemp: Seq[Double] = Seq(
      -16.9, -12.3, -5.1, 2.2, 8.8, 16.2, 22.9, 26.1, 26.4,
      26.1, 24.6, 21.4, 16.5, 9.9, 2.9, -6.9, -29.5, -42.3)

    TrainPolynomialCurve.polynomialCurves(valLatitude zip WeightedObservedPointsTemp)
  }

  /**
    * Get a maximum variable rate value with latitude value
    * by polynomial curves
    */
  private val TVariableRateCurves: PolynomialFunction = {

    // The data contains some actual measurements about albedo
    // I assume there is a relationship between the maximum variation range of temperature and albedo.
    val valAlbedo: Seq[Double] = Seq(
      0.589, 0.544, 0.452, 0.407, 0.357, 0.309, 0.272, 0.248, 0.254,
      0.241, 0.236, 0.251, 0.296, 0.358, 0.426, 0.513, 0.602, 0.617)

    TrainPolynomialCurve.polynomialCurves(valLatitude zip valAlbedo)
  }

  def TRange(latitude: Double): Double = {
    // Get average temperature
    val tempAvg = TemperatureAvgCurves.value(latitude)

    // Get the range value of temperature variable
    tempAvg * TVariableRateCurves.value(latitude)
  }

  def TRangeMaxMin(latitude: Double): (Double, Double) = {
    val absHalfRange = math.abs(TRange(latitude)) / 2
    val avgValue = TemperatureAvgCurves.value(latitude)
    (avgValue - absHalfRange, avgValue + absHalfRange)
  }
}
