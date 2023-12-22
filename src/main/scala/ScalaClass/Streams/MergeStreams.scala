import zio._
import zio.stream._

object MergeStreams extends ZIOAppDefault {

  val s1 = ZStream.fromIterable((0 to 5).toList).repeat(Schedule.forever)
  val s2 = ZStream.fromIterable((10 to 15).toList).repeat(Schedule.forever)
  val s3 = ZStream.fromIterable((100 to 105).toList).repeat(Schedule.forever)

  val merged = ZStream
    .mergeAllUnbounded(16)(s1, s2, s3)
    .take(50)
    .grouped(5)
    .foreach(chunk => Console.printLine(chunk.foldLeft(0)((a, b) => a + b)))

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    for {
      _ <- merged
    } yield ()
}
