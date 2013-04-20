package controllers

import play.api.mvc._
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.Files.TemporaryFile
import java.io.File

object ImageController extends Controller {

  private val DOWNLOAD_PATH: String = "/tmp/"

  def index(path: String) = Action {
    if (path == "default") Redirect(routes.Assets.at("img/default.png"))
    else {

      Ok.sendFile(
        content = new java.io.File(DOWNLOAD_PATH + path)
      )
    }
  }

  def handleUploadedFile(file: Option[FilePart[TemporaryFile]],
                                 failure: Unit => Result,
                                 success: Option[String] => Result): Result = {
    if(!file.isEmpty) {
      file.map { image =>
        val filename = image.filename

        image.ref.moveTo(new File(DOWNLOAD_PATH + filename), true)

        success(Some(filename))
      }.getOrElse {
        failure()
      }
    } else {
      success(None)
    }
  }
}