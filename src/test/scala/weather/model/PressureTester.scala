package weather.model

import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

class PressureTester extends FlatSpec with Matchers with PrivateMethodTester {
  "To any temperature and elevation" should "returns reasonable Atmospheric Pressure value" in {
    println("\n>> getPressureValue test start")
    CitySamples.cityTempSamples.foreach(t => {
      // Test atmospheric pressure
      // Average sea-level pressure is 1013.25 hPa and record highs close to 1085.0 hbar.
      // So the highest value should lower than 1013.25 hPa and the lowest value should
      // higher than 20 hPa considering the location of airport.
      PressureAlg.getPressureValue(t.temp, t.elevation) should be(1013.25 +- 20.0)
    })
    println(">> getPressureValue test end.\n")
  }
}
