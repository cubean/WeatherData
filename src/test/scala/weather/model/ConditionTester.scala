package weather.model

import org.scalatest.{FlatSpec, Matchers}

class ConditionTester extends FlatSpec with Matchers {
  "To any elevation" should "returns reasonable condition value" in {
    println("\n>> getConditionValue test start")
    CitySamples.cityHumiditySamples.map(t => {
      ConditionsAlg.getConditionValue(t.temp, t.humidity)
    }) should contain oneOf ("Rain", "Snow", "Sunny")
    println(">> getConditionValue test end.\n")
  }
}
