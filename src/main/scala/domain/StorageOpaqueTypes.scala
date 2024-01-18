package domain

import scala.language.implicitConversions

/*
  Types mainly used to represent quantity of energy / water. 
  The implicits toDouble are used to simplify the use of EnergyAmount / WaterAmount objects.
  We don't have to call the unapply function everytime we need the actual value.
  It also helps for our maths as we can just add EnergyAmount types together.

  Implicit is used here as well because during the refactoring process, we realised that depending on the scope of our file
  (for example, if "AutonomousHome" has direct access to the definition of these opaque types or not), the implicit conversion
  doesn't happen etc... The behaviour changes a lot depending of the file where everything is stored, even if it is public.
 */

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
