package domain

import scala.language.implicitConversions

opaque type TemperatureCelsius = Double
object TemperatureCelsius {
  def apply(value: Double): TemperatureCelsius = value
  def unapply(value: TemperatureCelsius): Option[Double] = Some(value)
  implicit def toDouble(cc: TemperatureCelsius): Double = unapply(cc).getOrElse(0.0)
}

opaque type PrecipitationAmount = Double
object PrecipitationAmount {
  def apply(value: Double): PrecipitationAmount = value
  def unapply(value: PrecipitationAmount): Option[Double] = Some(value)
  implicit def toDouble(cc: PrecipitationAmount): Double = unapply(cc).getOrElse(0.0)
}

opaque type WindSpeed = Double
object WindSpeed {
  def apply(value: Double): WindSpeed = value
  def unapply(value: WindSpeed): Option[Double] = Some(value)
  implicit def toDouble(cc: WindSpeed): Double = unapply(cc).getOrElse(0.0)
}

opaque type CloudCover = Int
object CloudCover {
  def apply(value: Int): CloudCover = value
  def unapply(value: CloudCover): Option[Int] = Some(value)
  implicit def toInt(cc: CloudCover): Int = unapply(cc).getOrElse(0)
}

opaque type Interval = Int
object Interval {
  def apply(value: Int): Interval = value
  def unapply(value: Interval): Option[Int] = Some(value)
  implicit def toInt(cc: Interval): Int = unapply(cc).getOrElse(0)
}

opaque type IsDay = Int
object IsDay {
  def apply(value: Int): IsDay = value
  def unapply(value: IsDay): Option[Int] = Some(value)
  implicit def toInt(cc: IsDay): Int = unapply(cc).getOrElse(0)
}