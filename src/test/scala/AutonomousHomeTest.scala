import org.scalatest.funsuite.AnyFunSuite
import Main.AutonomousHome
import Main._

class AutonomousHomeTest extends AnyFunSuite {

  test("solarPanelProduction should produce electricity based on cloud cover") {
    val home = new AutonomousHome
    assert(home.solarPanelProduction(CloudCover(30)) == EnergyAmount(70))
    assert(home.solarPanelProduction(CloudCover(80)) == EnergyAmount(20))
    assert(home.solarPanelProduction(CloudCover(100)) == EnergyAmount(0))
  }

  test("eolienneProduction should produce electricity based on wind speed") {
    val home = new AutonomousHome
    assert(home.eolienneProduction(WindSpeed(10)) == EnergyAmount(5))
    assert(home.eolienneProduction(WindSpeed(20)) == EnergyAmount(10))
    assert(home.eolienneProduction(WindSpeed(0)) == EnergyAmount(0))
  }

  test("precipitationProduction should produce water based on precipitation") {
    val home = new AutonomousHome
    assert(home.precipitationProduction(PrecipitationAmount(50)) == WaterAmount(50))
    assert(home.precipitationProduction(PrecipitationAmount(0)) == WaterAmount(0))
  }

  test("electriciteConsumption should consume electricity based on conditions") {
    val home = new AutonomousHome
    assert(home.electriciteConsumption(IsDay(1), CloudCover(80), TemperatureCelsius(20)) == EnergyAmount(5))
    assert(home.electriciteConsumption(IsDay(0), CloudCover(80), TemperatureCelsius(20)) == EnergyAmount(5))
    assert(home.electriciteConsumption(IsDay(1), CloudCover(50), TemperatureCelsius(6)) == EnergyAmount(11))
  }

  test("waterConsumption should consume water based on precipitation") {
    val home = new AutonomousHome
    assert(home.waterConsumption(PrecipitationAmount(0)) == WaterAmount(50))
    assert(home.waterConsumption(PrecipitationAmount(100)) == WaterAmount(0))
  }

  test("updateStorage should update the storage values correctly") {
    val home = new AutonomousHome
    home.updateStorage(CloudCover(30), WindSpeed(10), PrecipitationAmount(50), IsDay(1), TemperatureCelsius(20))

    assert(home.productedEnergy == EnergyAmount(75))
    assert(home.productedWater == WaterAmount(50))
    assert(home.consumedEnergy == EnergyAmount(0))
    assert(home.consumedWater == WaterAmount(0))

    assert(home.electricityStorage == EnergyAmount(575))
    assert(home.waterStorage == WaterAmount(200))
  }
}