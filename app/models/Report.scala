package models

import akka.http.scaladsl.model.DateTime

import java.util.UUID

case class Report(
                   id: Option[String] = Option(UUID.randomUUID.toString),
                   cityName: String,
                   weather: String,
                   weatherDescription: String,
                   kelvinTemp: Float,
                   humidity: Int,
                   message: String,
                   date: String,
                   responseCode :Int
                 )
