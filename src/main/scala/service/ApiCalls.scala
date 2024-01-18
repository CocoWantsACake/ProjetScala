package service

import zio.*
import zio.http.*

object ApiCalls {

  // Calling the open-meteo API and returning the result
  def fetchData() = {
    val url = URL
      .decode("https://api.open-meteo.com/v1/forecast?latitude=43.7392&longitude=0.0087&current=temperature_2m,is_day,precipitation,rain,showers,cloud_cover,wind_speed_10m&timezone=Europe%2FBerlin")
      .toOption
      .get // unsafe

    for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("/")
    } yield res
  }
}
