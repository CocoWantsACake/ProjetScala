import zio._
import zio.Console._
import zio.Duration._
import zio.http._
import zio.stream._
import MergeStreams._
import zio.json._

object Main extends ZIOAppDefault {

  case class CurrentUnits(
    time: String,
    interval: String,
    temperature_2m: String,
    is_day: String,
    precipitation: String,
    rain: String,
    showers: String,
    cloud_cover: String,
    wind_speed_10m: String
  )

  case class Current(
    time: String,
    interval: Int,
    temperature_2m: Double,
    is_day: Int,
    precipitation: Double,
    rain: Double,
    showers: Double,
    cloud_cover: Int,
    wind_speed_10m: Double
  )

  case class WeatherData(
    latitude: Double,
    longitude: Double,
    generationtime_ms: Double,
    utc_offset_seconds: Int,
    timezone: String,
    timezone_abbreviation: String,
    elevation: Double,
    current_units: CurrentUnits,
    current: Current
  )

  implicit val currentUnitsDecoder: JsonDecoder[CurrentUnits] = DeriveJsonDecoder.gen[CurrentUnits]
  implicit val currentDecoder: JsonDecoder[Current] = DeriveJsonDecoder.gen[Current]
  implicit val weatherDataDecoder: JsonDecoder[WeatherData] = DeriveJsonDecoder.gen[WeatherData]

  class AutonomousHome {
    var electricityStorage: Double = 500 // kWh
    var waterStorage: Double = 20 // Liters
    var productedEnergy: Double = 0 // kWh
    var productedWater: Double = 0 // Liters
    var consumedEnergy: Double = 0 // kWh
    var consumedWater: Double = 0 // Liters

    // Méthode pour produire de l'électricité par panneau solaire
    def solarPanelProduction(cloud_cover: Int): Double = {
      if (cloud_cover < 100) 100 - cloud_cover else 0
    }

    // Méthode pour produire de l'électricité par éolienne
    def eolienneProduction(windSpeed: Double): Double = {
      windSpeed / 2
    }

    // Méthode pour produire de l'eau
    def precipitationProduction(precipitation: Double): Double = {
      precipitation
    }

    // Méthode pour consommer de l'électricité
    def electriciteConsumption(isDay: Int, cloud_cover: Int, temperature: Double): Double = {
      if (isDay == 0 || cloud_cover > 70) 5 // kWh
      else if (temperature < 16) 10 + (16 - temperature) * 0.1 // kWh
      else 0
    }

    // Méthode pour consommer de l'eau
    def waterConsumption(precipitation: Double): Double = {
      if (precipitation == 0) 50 // ml
      else 0
    }

    // Méthode pour mettre à jour les stocks de ressources toutes les 30 secondes
    def updateStorage(
        cloudCover: Int,
        windSpeed: Double,
        precipitation: Double,
        isDay: Int,
        temperature: Double
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

  def printWeatherData(data: WeatherData) = {
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
  }


  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = {
    val autonomousHome = new AutonomousHome() // Créez une instance en dehors de la boucle
    
    val appLogic = for {
      _ <- ZStream(MergeStreams.mergeStreams())
        .repeat(Schedule.spaced(30.seconds))
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString
            decoded <- ZIO.fromEither(JsonDecoder[WeatherData].decodeJson(body))
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