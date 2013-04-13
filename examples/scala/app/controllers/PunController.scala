package controllers

import play.api.Play.current

import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import scala.slick.jdbc._

import models.Puns
import models.Pun

import java.io.File
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.Files.TemporaryFile

object PunController extends Controller {

  lazy val punForm = Form(
    mapping(
      "description" -> text
    )
    ((description) => Pun(None, description, None))
    ((pun: Pun) => Some(pun.description))
  )

  def index = Action { implicit request =>
    Ok(views.html.addPun(punForm))
  }

  def submit = Action(parse.multipartFormData) { implicit request =>
    DB.withTransaction { implicit session =>

      punForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.addPun(formWithErrors)),

        value => { ImageController.handleUploadedFile(request.body.file("image"),
          Unit => Redirect(routes.PunController.index).flashing("error" -> "Image upload impossible"),

          path => {
            Puns.insert(Pun(None, value.description, path))

            Redirect(routes.PunController.index).flashing("message" -> "Your Pun is a success !")
          })
        }
      )
    }
  }

  def delete(id: Int) = Action { implicit request =>
    DB.withTransaction { implicit session =>

      //findById(id).delete

      Redirect(routes.Application.index())
    }
  }
}