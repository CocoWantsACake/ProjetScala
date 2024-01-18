package service

import domain.WeatherOpaqueTypes._
import domain.StorageOpaqueTypes._

class AutonomousHome {
    var electricityStorage: EnergyAmount = 500 // kWh
    var waterStorage: WaterAmount = 20000 // Liters
    var productedEnergy: EnergyAmount = 0 // kWh
    var productedWater: EnergyAmount = 0 // Liters
    var consumedEnergy: WaterAmount = 0 // kWh
    var consumedWater: WaterAmount = 0 // Liters

    // Méthode pour produire de l'électricité par panneau solaire
    def solarPanelProduction(cloud_cover: CloudCover): EnergyAmount = {
      if (cloud_cover < 100) 100 - cloud_cover else 0
    }

    // Méthode pour produire de l'électricité par éolienne
    def eolienneProduction(windSpeed: WindSpeed): EnergyAmount = {
      windSpeed / 2
    }

    // Méthode pour produire de l'eau
    def precipitationProduction(precipitation: PrecipitationAmount): WaterAmount = {
      precipitation
    }

    // Méthode pour consommer de l'électricité
    def electriciteConsumption(isDay: IsDay, cloud_cover: CloudCover, temperature: TemperatureCelsius): EnergyAmount = {
      var totalConsumption: EnergyAmount = 0
      if (isDay == 1 && cloud_cover > 70 || isDay == 0) {
        totalConsumption += 5 // kWh
      }
      if (temperature < 16) {
        totalConsumption += 10 + (16 - temperature) * 0.1 // kWh
      }
      totalConsumption
    }

    // Méthode pour consommer de l'eau
    def waterConsumption(precipitation: PrecipitationAmount): WaterAmount = {
      precipitation match {
        case 0 => 50 // ml
        case _ => 0
      }
    }

    // Méthode pour mettre à jour les stocks de ressources toutes les 30 secondes
    def updateStorage(
      cloudCover: CloudCover,
      windSpeed: WindSpeed,
      precipitation: PrecipitationAmount,
      isDay: IsDay,
      temperature: TemperatureCelsius
    ): Unit = {
      val solarPanelProductionValue = solarPanelProduction(cloudCover)
      val eolienneProductionValue = eolienneProduction(windSpeed)
      val precipitationProductionValue = precipitationProduction(precipitation)
      val electriciteConsumptionValue = electriciteConsumption(isDay, cloudCover, temperature)
      val waterConsumptionValue = waterConsumption(precipitation)

      productedEnergy = solarPanelProductionValue + eolienneProductionValue
      productedWater = precipitationProductionValue
      consumedEnergy = electriciteConsumptionValue
      consumedWater = waterConsumptionValue

      electricityStorage = (electricityStorage + productedEnergy - consumedEnergy)
        .max(0)
        .min(10000) // Limitation à 10 000 kWh
      waterStorage = (waterStorage + productedWater - consumedWater)
        .max(0)
        .min(200) // Limitation à 200 L
    }
  }