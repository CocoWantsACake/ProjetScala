package domain

object EnergyAmount {
    opaque type EnergyAmount = Double
    def apply(value: Double): EnergyAmount = value
    def unapply(value: EnergyAmount): Option[Double] = Some(value)
  }

  object WaterAmount {
    opaque type WaterAmount = Double
    def apply(value: Double): WaterAmount = value
    def unapply(value: WaterAmount): Option[Double] = Some(value)
  }
