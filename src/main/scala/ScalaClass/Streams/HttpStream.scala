import zio._
import zio.console._
import zio.http._
import zio.stream._
import zio.duration._

object HttpStream extends App {

  def fetchData(): ZIO[Client with Console, Throwable, Response] = {
    val url = URL
      .decode(
        // appel de l'api open-meteo
        "https://api.open-meteo.com/v1/forecast?latitude=43.7392&longitude=0.0087&current=temperature_2m,is_day,precipitation,rain,showers,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin"
      )
      .toOption
      .get // unsafe

    for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("/")
    } yield res
  }

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    fetchData().foldM(
      error => console.putStrLn(s"Execution failed with: $error").as(ExitCode.failure),
      _ => console.putStrLn("Execution succeeded").as(ExitCode.success)
    )
}
