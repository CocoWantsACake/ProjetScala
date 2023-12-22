import zio._
import zio.stream._

object SimpleStream extends ZIOAppDefault {

  override val run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- ZStream
        .fromIterable((1 to 10).toList)
        .take(5)
        .tap(x => Console.printLine(s"before mapping: $x"))
        .map(_ * 2)
        .tap(x => Console.printLine(s"after mapping: $x"))
        .map(_.toString)
        .foreach(Console.printLine(_))
    } yield ()
}