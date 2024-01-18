package domain

import scala.annotation.tailrec
import scala.language.implicitConversions

opaque type EnergyAmount = Double
object EnergyAmount {
  def apply(value: Double): EnergyAmount = value
  def unapply(value: EnergyAmount): Option[Double] = Some(value)
  implicit def toDouble(cc: EnergyAmount): Double = unapply(cc).getOrElse(0.0)
}

opaque type WaterAmount = Double
object WaterAmount {
  def apply(value: Double): WaterAmount = value
  def unapply(value: WaterAmount): Option[Double] = Some(value)
  implicit def toDouble(cc: WaterAmount): Double = unapply(cc).getOrElse(0.0)
}
