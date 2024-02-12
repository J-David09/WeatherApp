package controllers

import models.Report
import play.api.mvc._
import play.api.libs.json.Json
import repository.ReportRepository
import services.ReportService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ReportController @Inject()(cc: ControllerComponents, reportRepository: ReportRepository, reportService: ReportService)
  extends AbstractController(cc)  {

  implicit var serializador = Json.format[Report]

  def getReports = Action.async {
    reportRepository.getAll.map(report => {
      val jsonReports = Json.obj(
        "data" -> report,
        "message" -> "Successfully retrieved reports."
      )
      Ok(jsonReports)
    })
  }

  def getAndCreateReport (location : String) = Action.async {
    val reportObj = reportService.getWeatherByLocation(location)
    reportObj.responseCode match {
      case 200 =>
        reportRepository
          .create(reportObj)
          .map(report => {
            val jsonReport = Json.obj(
              "data" -> report,
              "message" -> "report found"
            )
            Ok(jsonReport)
          })
      case _ =>
        val jsonReport = Json.obj(
          "data" -> "",
          "message" -> reportObj.message
        )
        Future.successful(InternalServerError(jsonReport))
    }

  }

}
