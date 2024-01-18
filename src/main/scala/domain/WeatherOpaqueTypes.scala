package domain

import scala.language.implicitConversions
import zio.json._

opaque type TemperatureCelsius = Double
object TemperatureCelsius {
  def apply(value: Double): TemperatureCelsius = value
  def unapply(value: TemperatureCelsius): Option[Double] = Some(value)
  implicit def toDouble(cc: TemperatureCelsius): Double = unapply(cc).getOrElse(0.0)
  //HAHAHHAHAA Si on utilise "JsonDecoder[Double]" ça ne marche pas, mais JsonDecoder.double fonctionne, HAHHA JE DEVIENDRE FOU CA FAIT 3 HEURES JHAHAHHAHAUAZ
  implicit val decoder: JsonDecoder[TemperatureCelsius] = JsonDecoder.double.map(value => TemperatureCelsius(value))
}

opaque type PrecipitationAmount = Double
object PrecipitationAmount {
  def apply(value: Double): PrecipitationAmount = value
  def unapply(value: PrecipitationAmount): Option[Double] = Some(value)
  implicit def toDouble(cc: PrecipitationAmount): Double = unapply(cc).getOrElse(0.0)
  implicit val decoder: JsonDecoder[PrecipitationAmount] = JsonDecoder.double.map(value => PrecipitationAmount(value))
}

opaque type WindSpeed = Double
object WindSpeed {
  def apply(value: Double): WindSpeed = value
  def unapply(value: WindSpeed): Option[Double] = Some(value)
  implicit def toDouble(cc: WindSpeed): Double = unapply(cc).getOrElse(0.0)
  implicit val decoder: JsonDecoder[WindSpeed] = JsonDecoder.double.map(value => WindSpeed(value))
}

opaque type CloudCover = Int
object CloudCover {
  def apply(value: Int): CloudCover = value
  def unapply(value: CloudCover): Option[Int] = Some(value)
  implicit def toInt(cc: CloudCover): Int = unapply(cc).getOrElse(0)
  implicit val decoder: JsonDecoder[CloudCover] = JsonDecoder.int.map(value => CloudCover(value))
}

opaque type Interval = Int
object Interval {
  def apply(value: Int): Interval = value
  def unapply(value: Interval): Option[Int] = Some(value)
  implicit def toInt(cc: Interval): Int = unapply(cc).getOrElse(0)
  implicit val decoder: JsonDecoder[Interval] = JsonDecoder.int.map(value => Interval(value))
}

opaque type IsDay = Int
object IsDay {
  def apply(value: Int): IsDay = value
  def unapply(value: IsDay): Option[Int] = Some(value)
  implicit def toInt(cc: IsDay): Int = unapply(cc).getOrElse(0)
  implicit val decoder: JsonDecoder[IsDay] = JsonDecoder.int.map(value => IsDay(value))
}