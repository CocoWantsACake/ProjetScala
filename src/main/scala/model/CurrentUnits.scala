package model

import zio.json.*

// Just the units indicated inside the JSON returned by the API call
final case class CurrentUnits(
    time: String,
    interval: String,
    temperature_2m: String,
    is_day: String,
    precipitation: String,
    rain: String,
    showers: String,
    cloud_cover: String,
    wind_speed_10m: String
)

object CurrentUnits {
  implicit val decoder: JsonDecoder[CurrentUnits] = DeriveJsonDecoder.gen[CurrentUnits]
}