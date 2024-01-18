package model

import zio.json._

// Main class to decode the content of the JSON returned by the call to the open-meteo API
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

object WeatherLocationData {
  implicit val decoder: JsonDecoder[WeatherLocationData] = DeriveJsonDecoder.gen[WeatherLocationData]
}
