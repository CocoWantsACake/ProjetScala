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


  def printWeatherData(data: WeatherData) = {
    Console.printLine(
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
    )
  }
    
   

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = {
    val appLogic = for {
      _ <- ZStream(MergeStreams.mergeStreams())
        .repeat(Schedule.spaced(30.seconds))
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString
            decoded <- ZIO.fromEither(JsonDecoder[WeatherData].decodeJson(body))
            _ <- printWeatherData(decoded)
          } yield body
        }
        .foreach(Console.printLine(_))
    } yield ()

    appLogic.provide(Client.default, Scope.default)
  }
}