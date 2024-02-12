package models

import slick.lifted.Tag

import slick.jdbc.SQLiteProfile.api._

class TableReport(tag: Tag) extends Table[Report](tag, "TableReport") {
  def id = column[String]("id", O.PrimaryKey)

  def cityName = column[String]("cityName")

  def weather = column[String]("weather")

  def weatherDescription = column[String]("weatherDescription")

  def kelvinTemp = column[Float]("kelvinTemp")

  def humidity = column[Int]("humidity")

  def message = column[String]("message")

  def date = column[String]("date")

  def responseCode = column[Int]("responseCode")

  def * =
    (id.?, cityName, weather, weatherDescription, kelvinTemp, humidity, message, date, responseCode) <> (Report.tupled, Report.unapply)
}
