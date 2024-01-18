package service

import domain._

class AutonomousHome {
    var electricityStorage: EnergyAmount = EnergyAmount(500) // kWh
    var waterStorage: WaterAmount = WaterAmount(20000) // Liters
    var producedEnergy: EnergyAmount = EnergyAmount(0) // kWh
    var producedWater: WaterAmount = WaterAmount(0) // Liters
    var consumedEnergy: EnergyAmount = EnergyAmount(0) // kWh
    var consumedWater: WaterAmount = WaterAmount(0) // Liters

    // Method to produce electricity with a solar panel
    def solarPanelProduction(cloud_cover: CloudCover): EnergyAmount = {
      if (cloud_cover < 100) EnergyAmount(100 - cloud_cover) else EnergyAmount(0)
    }

    // Method to produce electricity with a wind turbine
    def windTurbineProduction(windSpeed: WindSpeed): EnergyAmount = {
      EnergyAmount(windSpeed / 2)
    }

    // Method to produce water
    def precipitationProduction(precipitation: PrecipitationAmount): WaterAmount = {
      WaterAmount(precipitation)
    }

    // Method to consume electricity
    def electricityConsumption(isDay: IsDay, cloud_cover: CloudCover, temperature: TemperatureCelsius): EnergyAmount = {
      var totalConsumption: Double = 0
      if (isDay == IsDay(1) && cloud_cover > 70 || isDay == IsDay(0)) {
        totalConsumption += 5 // kWh
      }
      if (temperature < 16) {
        totalConsumption += 10 + (16 - temperature) * 0.1 // kWh
      }
      EnergyAmount(totalConsumption)
    }

    // Method to consume water
    def waterConsumption(precipitation: PrecipitationAmount): WaterAmount = {
      precipitation match {
        case WaterAmount(0) => WaterAmount(50) // ml
        case _ => WaterAmount(0)
      }
    }

    // Method to update the stocks of resources
    def updateStorage(cloudCover: CloudCover, windSpeed: WindSpeed, precipitation: PrecipitationAmount, isDay: IsDay, temperature: TemperatureCelsius): Unit = {
      val solarPanelProductionValue = solarPanelProduction(cloudCover)
      val windTurbineProductionValue = windTurbineProduction(windSpeed)
      val precipitationProductionValue = precipitationProduction(precipitation)
      val electricityConsumptionValue = electricityConsumption(isDay, cloudCover, temperature)
      val waterConsumptionValue = waterConsumption(precipitation)

      producedEnergy = EnergyAmount(solarPanelProductionValue + windTurbineProductionValue)
      producedWater = precipitationProductionValue
      consumedEnergy = electricityConsumptionValue
      consumedWater = waterConsumptionValue

      electricityStorage = EnergyAmount((electricityStorage + producedEnergy - consumedEnergy).max(0).min(10000))
      waterStorage = WaterAmount((waterStorage + producedWater - consumedWater).max(0).min(200))
    }
  }