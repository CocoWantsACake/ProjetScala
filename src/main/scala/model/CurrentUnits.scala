package model

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
