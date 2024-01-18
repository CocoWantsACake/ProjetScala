package model

import java.time.LocalDateTime
import domain.WeatherOpaqueTypes._

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
