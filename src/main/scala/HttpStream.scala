import zio._
import zio.http._
import zio.stream._
import zio.Duration._

object HttpStream extends ZIOAppDefault {

  def fetchData() = {
    val url = URL
      .decode(
        //appel de l'api open-meteo
        "https://api.open-meteo.com/v1/forecast?latitude=43.7392&longitude=0.0087&current=temperature_2m,is_day,precipitation,rain,showers,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin"
      )
      .toOption
      .get // unsafe

    for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("/")
    } yield res
  }

  override def run: ZIO[Any, Any, Unit] =
    val appLogic = for {
      _ <- ZStream(fetchData())
        .repeat(Schedule.spaced(30.seconds))
        // .groupedWithin(30, 10.seconds)
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString
            //call the createenergy function with in parameters the temperature and the rain that we get from the api
            _ <- createEnergy(body.temperature_2m, body.rain)
            _ <- Console.printLine(s"body size is: ${body.length}")
          } yield body
        }
        .foreach(Console.printLine(_))
    } yield ()

    appLogic.provide(Client.default, Scope.default)

  //create a function in scala that takes 2 string parameters : temperature and rain, in the function convert the string to int
  def createEnergy(temperature: String, rain: String) = {
    //create a variable
    var energy = 0
    //create a loop
    for (i <- 1 to 10) {
      //add 1 to energy
      energy = energy + 1
      //print energy
      println(energy)
    }
  }
}
