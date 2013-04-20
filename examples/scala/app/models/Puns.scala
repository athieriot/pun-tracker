package models

import play.api.db.slick.Config.driver.simple._

case class Pun(id: Option[Int], description: String, imagePath: Option[String], rating: Int = 0)

object Puns extends Table[Pun]("puns") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def description = column[String]("description")
  def imagePath = column[String]("imagePath", O.Nullable)
  def rating = column[Int]("rating")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = id.? ~ description ~ imagePath.? ~ rating <> (Pun, Pun.unapply _)

  // Utility methods
  def findById(id: Int) = {
    for (p <- Puns if p.id is id) yield p
  }

  // Utility methods
  def findByIdWithRating(id: Int) = {
    for (p <- Puns if p.id is id) yield p.rating
  }

  def findAll = {
    for (p <- Puns) yield p
  }
}