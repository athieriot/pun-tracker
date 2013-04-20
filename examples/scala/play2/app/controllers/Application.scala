package controllers

import play.api.Play.current

import play.api.mvc._

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

import models.Puns

object Application extends Controller {

  def index = Action {
    DB.withTransaction { implicit session =>

      Ok(views.html.index(Puns.findAll.list))
    }
  }
}