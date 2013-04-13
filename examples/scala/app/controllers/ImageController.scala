package controllers

import play.api.Play.current

import play.api.mvc._

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

import models.Puns


object ImageController extends Controller {

  def index(path: String) = Action {
    Ok.sendFile(
      content = new java.io.File("/tmp/" + path)
    )
  }
}