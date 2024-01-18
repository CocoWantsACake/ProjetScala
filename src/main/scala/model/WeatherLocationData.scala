package model

final case class WeatherLocationData(
    latitude: Double,
    longitude: Double,
    generationtime_ms: Double,
    utc_offset_seconds: Int,
    timezone: String,
    timezone_abbreviation: String,
    elevation: Double,
    current_units: CurrentUnits,
    current: Current
)
