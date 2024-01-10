import zio._
import zio.Console._
import zio.Duration._
import zio.http._
import zio.stream._
import HttpStream._
import SimpleStream._

object MergeStreams {

  def mergeStreams() = {
    

    val s1 = HttpStream.fetchData()
    // val s2 = SimpleStream.createData()

    // s1.foreach { res =>
    //   for {
    //     body <- res.body.asString
    //     _    <- Console.printLine(s"body size is: ${body.length}")
    //   } yield ()
    // }

    // Utiliser foreach pour imprimer chaque élément du flux s2
    //s2.foreach(Console.printLine(_))


    s1
  } 
}
