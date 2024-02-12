package repository

import models.{Report, TableReport}

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class ReportRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider,  cc: ControllerComponents)
                                 (implicit  ec: ExecutionContext)
    extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {

  private lazy val movieQuery = TableQuery[TableReport]

  def dbInit: Future[Unit] = {
    val createSchema = movieQuery.schema.createIfNotExists
    db.run(createSchema)
  }

  def getAll = {
    val allReports = movieQuery.sortBy(_.id)
    db.run(allReports.result)
  }

  def create (report: Report) = {
    val insert = movieQuery += report
    db.run(insert)
      .flatMap(_ => getOne(report.id.getOrElse("")))
  }

  def getOne (id : String) = {
    val reportById = movieQuery.filter(_.id === id)
    db.run(reportById.result.headOption)
  }

}
