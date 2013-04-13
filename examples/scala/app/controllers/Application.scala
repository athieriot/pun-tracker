package controllers

import play.api.Play.current

import play.api._
import play.api.mvc._

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

import models.Puns
import models.Pun

object Application extends Controller {

  def index = Action {
    DB.withTransaction { implicit session =>

      var puns = for (p <- Puns) yield p

      Ok(views.html.index(puns.list))
    }
  }
}