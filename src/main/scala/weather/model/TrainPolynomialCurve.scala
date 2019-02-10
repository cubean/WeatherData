package weather.model

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction
import org.apache.commons.math3.fitting.{PolynomialCurveFitter, WeightedObservedPoints}

/**
  * Train polynomial curve
  */
object TrainPolynomialCurve {
  /**
    *  Calculate polynomial curves
    *
    *  @param points the weighted observed points
    */
  def polynomialCurves(points: Seq[(Double, Double)]): PolynomialFunction = {
    val obs = new WeightedObservedPoints()
    val polynomialDegree = 3

    points.foreach(x => obs.add(x._1, x._2))

    // Instantiate a third-degree polynomial fitter.
    val fitter = PolynomialCurveFitter.create(polynomialDegree)

    // Retrieve fitted parameters (coefficients of the polynomial function).
    val coeff = fitter.fit(obs.toList)

    // Get new polynomial function
    new PolynomialFunction(coeff)
  }
}
