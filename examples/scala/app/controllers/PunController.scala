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
        value => {

          if(!request.body.file("picture").isEmpty) {
            request.body.file("picture").map { picture =>
              import java.io.File
              val filename = picture.filename
              val contentType = picture.contentType
              picture.ref.moveTo(new File("/tmp/" + filename))

              Puns.insert(Pun(None, value.description, Some(picture.filename)))

              Redirect(routes.PunController.index).flashing("message" -> "Your Pun is a success !")
            }.getOrElse {
              Redirect(routes.PunController.index).flashing("error" -> "Image upload impossible")
            }
          } else {
            Puns.insert(Pun(None, value.description, None))

            Redirect(routes.PunController.index).flashing("message" -> "Your Pun is a success !")
          }
        }
      )
    }
  }

  def delete(id: Int) = Action { implicit request =>
    DB.withTransaction { implicit session =>

      val findById = for {
        id <- Parameters[Int]
        p <- Puns if p.id is id
      } yield p

      //To implement
      //findById(id).delete

      Redirect(routes.Application.index())
    }
  }
}