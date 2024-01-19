package app

import zio.*
import zio.Console.*
import zio.http.*
import zio.stream.*
import zio.json.*
import service.{ApiCalls, AutonomousHome}
import service.ApiCalls.*
import model.WeatherLocationData

object Main extends ZIOAppDefault {

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = {
    val autonomousHome = new AutonomousHome()
    
    val appLogic = for {
      _ <- ZStream(ApiCalls.fetchData())
        .repeat(Schedule.spaced(30.seconds))
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString
            decoded <- ZIO.fromEither(JsonDecoder[WeatherLocationData].decodeJson(body))

            // Calling updateStorage with the queried data
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