package domain

object TemperatureCelsius {
  opaque type TemperatureCelsius = Double
  def apply(value: Double): TemperatureCelsius = value
  def unapply(value: TemperatureCelsius): Option[Double] = Some(value)
}

object PrecipitationAmount {
  opaque type PrecipitationAmount = Double
  def apply(value: Double): PrecipitationAmount = value
  def unapply(value: PrecipitationAmount): Option[Double] = Some(value)
}

object WindSpeed {
  opaque type WindSpeed = Double
  def apply(value: Double): WindSpeed = value
  def unapply(value: WindSpeed): Option[Double] = Some(value)
}

object CloudCover {
  opaque type CloudCover = Int
  def apply(value: Int): CloudCover = value
  def unapply(value: CloudCover): Option[Int] = Some(value)
}

object Interval {
  opaque type Interval = Int
  def apply(value: Int): Interval = value
  def unapply(value: Interval): Option[Int] = Some(value)
}

object IsDay {
  opaque type IsDay = Int
  def apply(value: Int): IsDay = value
  def unapply(value: IsDay): Option[Int] = Some(value)
}