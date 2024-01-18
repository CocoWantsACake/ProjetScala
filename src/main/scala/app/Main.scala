import zio._
import zio.Console._
import zio.Duration._
import zio.http._
import zio.stream._
import zio.json._
import java.time.LocalDateTime
import service.AutonomousHome
import HttpStream._
import model.WeatherLocationData

object Main extends ZIOAppDefault {

  // opaque type TemperatureCelsius = Double
  // opaque type PrecipitationAmount = Double
  // opaque type WindSpeed = Double
  // opaque type CloudCover = Int
  // opaque type Interval = Int
  // opaque type IsDay = Int
  // opaque type EnergyAmount = Double
  // opaque type WaterAmount = Double
  // // opaque types

  // // object companion
  // object TemperatureCelsius {
  //   def apply(value: Double): TemperatureCelsius = value
  //   def unapply(value: TemperatureCelsius): Option[Double] = Some(value)
  // }

  // object PrecipitationAmount {
  //   def apply(value: Double): PrecipitationAmount = value
  //   def unapply(value: PrecipitationAmount): Option[Double] = Some(value)
  // }

  // object WindSpeed {
  //   def apply(value: Double): WindSpeed = value
  //   def unapply(value: WindSpeed): Option[Double] = Some(value)
  // }

  // object CloudCover {
  //   def apply(value: Int): CloudCover = value
  //   def unapply(value: CloudCover): Option[Int] = Some(value)
  // }

  // object Interval {
  //   def apply(value: Int): Interval = value
  //   def unapply(value: Interval): Option[Int] = Some(value)
  // }

  // object IsDay {
  //   def apply(value: Int): IsDay = value
  //   def unapply(value: IsDay): Option[Int] = Some(value)
  // }

  // object EnergyAmount {
  //   def apply(value: Double): EnergyAmount = value
  //   def unapply(value: EnergyAmount): Option[Double] = Some(value)
  // }

  // object WaterAmount {
  //   def apply(value: Double): WaterAmount = value
  //   def unapply(value: WaterAmount): Option[Double] = Some(value)
  // }

  // case class CurrentUnits(
  //   time: String,
  //   interval: String,
  //   temperature_2m: String,
  //   is_day: String,
  //   precipitation: String,
  //   rain: String,
  //   showers: String,
  //   cloud_cover: String,
  //   wind_speed_10m: String
  // )

  // case class Current(
  //   time: LocalDateTime,
  //   interval: Interval,
  //   temperature_2m: TemperatureCelsius,
  //   is_day: IsDay,
  //   precipitation: PrecipitationAmount,
  //   rain: PrecipitationAmount,
  //   showers: PrecipitationAmount,
  //   cloud_cover: CloudCover,
  //   wind_speed_10m: WindSpeed
  // )

  // case class WeatherLocationData(
  //   latitude: Double,
  //   longitude: Double,
  //   generationtime_ms: Double,
  //   utc_offset_seconds: Int,
  //   timezone: String,
  //   timezone_abbreviation: String,
  //   elevation: Double,
  //   current_units: CurrentUnits,
  //   current: Current
  // )

  // // mettre implicite dans les object companions
  // implicit val currentUnitsDecoder: JsonDecoder[CurrentUnits] = DeriveJsonDecoder.gen[CurrentUnits]
  // implicit val currentDecoder: JsonDecoder[Current] = DeriveJsonDecoder.gen[Current]
  // implicit val weatherDataDecoder: JsonDecoder[WeatherLocationData] = DeriveJsonDecoder.gen[WeatherLocationData]

  // class AutonomousHome {
  //   var electricityStorage: EnergyAmount = 500 // kWh
  //   var waterStorage: WaterAmount = 20000 // Liters
  //   var productedEnergy: EnergyAmount = 0 // kWh
  //   var productedWater: EnergyAmount = 0 // Liters
  //   var consumedEnergy: WaterAmount = 0 // kWh
  //   var consumedWater: WaterAmount = 0 // Liters

  //   // Méthode pour produire de l'électricité par panneau solaire
  //   def solarPanelProduction(cloud_cover: CloudCover): EnergyAmount = {
  //     if (cloud_cover < 100) 100 - cloud_cover else 0
  //   }

  //   // Méthode pour produire de l'électricité par éolienne
  //   def eolienneProduction(windSpeed: WindSpeed): EnergyAmount = {
  //     windSpeed / 2
  //   }

  //   // Méthode pour produire de l'eau
  //   def precipitationProduction(precipitation: PrecipitationAmount): WaterAmount = {
  //     precipitation
  //   }

  //   // Méthode pour consommer de l'électricité
  //   def electriciteConsumption(isDay: IsDay, cloud_cover: CloudCover, temperature: TemperatureCelsius): EnergyAmount = {
  //     var totalConsumption: EnergyAmount = 0
  //     if (isDay == 1 && cloud_cover > 70 || isDay == 0) {
  //       totalConsumption += 5 // kWh
  //     }
  //     if (temperature < 16) {
  //       totalConsumption += 10 + (16 - temperature) * 0.1 // kWh
  //     }
  //     totalConsumption
  //   }

  //   // Méthode pour consommer de l'eau
  //   def waterConsumption(precipitation: PrecipitationAmount): WaterAmount = {
  //     precipitation match {
  //       case 0 => 50 // ml
  //       case _ => 0
  //     }
  //   }

  //   // Méthode pour mettre à jour les stocks de ressources toutes les 30 secondes
  //   def updateStorage(
  //     cloudCover: CloudCover,
  //     windSpeed: WindSpeed,
  //     precipitation: PrecipitationAmount,
  //     isDay: IsDay,
  //     temperature: TemperatureCelsius
  //   ): Unit = {
  //     val solarPanelProductionValue = solarPanelProduction(cloudCover)
  //     val eolienneProductionValue = eolienneProduction(windSpeed)
  //     val precipitationProductionValue = precipitationProduction(precipitation)
  //     val electriciteConsumptionValue = electriciteConsumption(isDay, cloudCover, temperature)
  //     val waterConsumptionValue = waterConsumption(precipitation)

  //     productedEnergy = solarPanelProductionValue + eolienneProductionValue
  //     productedWater = precipitationProductionValue
  //     consumedEnergy = electriciteConsumptionValue
  //     consumedWater = waterConsumptionValue

  //     electricityStorage = (electricityStorage + productedEnergy - consumedEnergy)
  //       .max(0)
  //       .min(10000) // Limitation à 10 000 kWh
  //     waterStorage = (waterStorage + productedWater - consumedWater)
  //       .max(0)
  //       .min(200) // Limitation à 200 L
  //   }
  // }

  //def printWeatherData(data: WeatherLocationData) = {
    //placer dna sune autrre fonction
    /*Console.printLine(
      s"""
         |Latitude: ${data.latitude}
         |Longitude: ${data.longitude}
         |Generation Time (ms): ${data.generationtime_ms}
         |UTC Offset (seconds): ${data.utc_offset_seconds}
         |Timezone: ${data.timezone}
         |Timezone Abbreviation: ${data.timezone_abbreviation}
         |Elevation: ${data.elevation}
         |Current Units:
         |  Time: ${data.current_units.time}
         |  Interval: ${data.current_units.interval}
         |  Temperature 2m: ${data.current_units.temperature_2m}
         |  Is Day: ${data.current_units.is_day}
         |  Precipitation: ${data.current_units.precipitation}
         |  Rain: ${data.current_units.rain}
         |  Showers: ${data.current_units.showers}
         |  Cloud Cover: ${data.current_units.cloud_cover}
         |  Wind Speed 10m: ${data.current_units.wind_speed_10m}
         |Current:
         |  Time: ${data.current.time}
         |  Interval: ${data.current.interval}
         |  Temperature 2m: ${data.current.temperature_2m}
         |  Is Day: ${data.current.is_day}
         |  Precipitation: ${data.current.precipitation}
         |  Rain: ${data.current.rain}
         |  Showers: ${data.current.showers}
         |  Cloud Cover: ${data.current.cloud_cover}
         |  Wind Speed 10m: ${data.current.wind_speed_10m}
         |""".stripMargin
    )*/
  //}


  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = {
    val autonomousHome = new AutonomousHome() // Créez une instance en dehors de la boucle
    
    val appLogic = for {
      _ <- ZStream(HttpStream.fetchData())
        .repeat(Schedule.spaced(30.seconds))
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString
            decoded <- ZIO.fromEither(JsonDecoder[WeatherLocationData].decodeJson(body))
            //_ <- printWeatherData(decoded)

            // Appel de updateStorage avec les données météorologiques actuelles
            _ = autonomousHome.updateStorage(
              decoded.current.cloud_cover,
              decoded.current.wind_speed_10m,
              decoded.current.precipitation,
              decoded.current.is_day,
              decoded.current.temperature_2m
            )

            _ <- Console.printLine(s"Electricity Storage: ${autonomousHome.electricityStorage} kWh")
            _ <- Console.printLine(s"Water Storage: ${autonomousHome.waterStorage} Liters")
          } yield body
        }
        .foreach(Console.printLine(_))
    } yield ()

    appLogic.provide(Client.default, Scope.default)
  }
}