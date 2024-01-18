package model

import java.time.LocalDateTime
import domain._
import zio.json.*

// Actual fields that we are interested in while calling the open-meteo API
final case class Current(
    time: LocalDateTime,
    interval: Interval,
    temperature_2m: TemperatureCelsius,
    is_day: IsDay,
    precipitation: PrecipitationAmount,
    rain: PrecipitationAmount,
    showers: PrecipitationAmount,
    cloud_cover: CloudCover,
    wind_speed_10m: WindSpeed
)

object Current {
  implicit val decoder: JsonDecoder[Current] = DeriveJsonDecoder.gen[Current]
}