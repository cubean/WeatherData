package weather.calculation

import org.scalatest.{FlatSpec, Matchers}
import weather.model.CitySamples

class WeatherCalTester extends FlatSpec with Matchers {
  "To any city and local date time" should "returns reasonable weather info" in {
    println("\n>> getWeatherInfo test start")
    CitySamples.cityWeatherSamples.foreach { w =>
      w should not be None
    }
    println(">> getWeatherInfo test end.\n")
  }
}
