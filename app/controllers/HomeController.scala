package controllers

import javax.inject._
import play.api.mvc._
import repository.ReportRepository

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()( cc: ControllerComponents, reportRepository: ReportRepository)
  extends AbstractController(cc)  {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def dbInit() = Action.async { request =>
    reportRepository.dbInit
      .map(_ => Created("Tabla creada"))
      .recover{ex =>
        play.Logger.of("dbInit").debug("Error en dbInit", ex)
        InternalServerError(s"Hubo un error")
      }
  }
}
