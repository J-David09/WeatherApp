package services

import Clients.WeatherApiClient
import akka.http.scaladsl.model.DateTime
import models.Report
import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import slick.lifted.Functions.currentDate

import scala.collection.mutable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReportService @Inject()(weatherApiClient: WeatherApiClient) {

  private val cache = mutable.Map[String, (Report, LocalDateTime)]()

  def getWeatherByLocation(location: String) = {
    val now = LocalDateTime.now()
    cache.get(location) match {
      case Some((report, expirationTime)) if expirationTime.isAfter(now) =>
        report
      case _ =>
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
        val currentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        val report = codResponse match {
          case 200 =>
            val name = (content \ "name").as[String]
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
          case _ => new Report(
            id = None,
            cityName = "",
            weather = "",
            weatherDescription = "",
            kelvinTemp = 0,
            humidity = 0,
            message = (content \ "message").as[String],
            date = currentDate,
            responseCode = codResponse
          )
        }

        cache(location) = (report, now.plusMinutes(1))
        report
    }
  }

  val cleaner = new Runnable {
    def run(): Unit = {
      val now = LocalDateTime.now()
      cache.retain((_, v) => v._2.isAfter(now))
    }
  }

  val scheduler = java.util.concurrent.Executors.newSingleThreadScheduledExecutor()
  scheduler.scheduleAtFixedRate(cleaner, 1, 1, TimeUnit.MINUTES)
}

