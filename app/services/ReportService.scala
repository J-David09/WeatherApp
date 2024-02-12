package services

import Clients.WeatherApiClient
import akka.http.scaladsl.model.DateTime
import models.Report
import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import slick.lifted.Functions.currentDate

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class ReportService @Inject()(weatherApiClient: WeatherApiClient) {

  def getWeatherByLocation(location: String) = {
    val content = weatherApiClient.getWeather(location)
    val codResponse: Int = (content \ "cod").asOpt[JsValue] match {
      case Some(jsValue) =>
        jsValue match {
          case JsNumber(num) => num.intValue
          case JsString(str) => str.toIntOption.getOrElse(0)
          case _ => 0
        }
      case None => 0
    }
    val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    codResponse match {
      case 200 => {
        val name = (content \ "name").as [String]
        val weather = (content \ "weather").as[Seq[JsObject]]
        val weatherMain = (weather.head \ "main").as[String]
        val description = (weather.head \ "description").as[String]
        val temp = (content \ "main" \ "temp").as[Float]
        val humidity = (content \ "main" \ "humidity").as[Int]

        new Report(
          id = Some(UUID.randomUUID().toString),
          cityName = name,
          weather = weatherMain,
          weatherDescription = description,
          kelvinTemp = temp,
          humidity = humidity,
          message = "succesfully report",
          date = currentDate,
          responseCode = codResponse
        )
      }
      case _ => new Report(
        id = None,
        cityName = "",
        weather = "",
        weatherDescription = "",
        kelvinTemp = 0,
        humidity = 0,
        message = (content \ "message").as [String],
        date = currentDate,
        responseCode = codResponse
      )
    }
  }

}

