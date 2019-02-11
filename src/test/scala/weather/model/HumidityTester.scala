package weather.model

import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

class HumidityTester extends FlatSpec with Matchers with PrivateMethodTester {
  "To any temperature and pressure" should "returns reasonable humidity value" in {
    println("\n>> getHumidityValue test start")
    CitySamples.cityHumiditySamples.foreach(t => {
      HumidityAlg.getHumidityValue(t.temp, t.pressure) should (be > 0.0 and be <= 100.0)
    })
    println(">> getHumidityValue test end.\n")
  }
}
