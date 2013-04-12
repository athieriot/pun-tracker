package models

import play.api.db.slick.Config.driver.simple._

case class Pun(id: Option[Int], description: String)

object Puns extends Table[Pun]("puns") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def description = column[String]("description")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = id.? ~ description <> (Pun, Pun.unapply _)
}