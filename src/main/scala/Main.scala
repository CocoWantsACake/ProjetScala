import zio._
import zio.Console._
import zio.Duration._
import zio.http._
import zio.stream._
import MergeStreams._

object Main extends ZIOAppDefault {
  
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = {
    val appLogic = for {
      _ <- ZStream(MergeStreams.mergeStreams())
        .repeat(Schedule.spaced(30.seconds))
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString
            _ <- Console.printLine(s"body size is: ${body.length}")
          } yield body
        }
        .foreach(Console.printLine(_))
    } yield ()
  
    appLogic.provide(Client.default, Scope.default)
  }

}
