package com.ifunsoftware.c3web.routing.files

import java.util.Calendar

import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models._
import com.ifunsoftware.c3web.models.FileEntryJson._
import com.ifunsoftware.c3web.models.MetadataEntryJson._
import com.ifunsoftware.c3web.service.{ FilesService }
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Created by alexander on 15.11.15.
 */

/**
 * Factory method for Props configuration files for actors
 */
object FileRoute {
  def props: Props = Props(new FileRoute())
}

/**
 * Actor that handles requests that begin with "file" (certain file)
 */
class FileRoute() extends Actor with FileRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(fileRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait FileRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[FileRouteTrait])

  val fileRoute = {
    get {
      pathEnd {
        complete(StatusCodes.NoContent)
      } ~
        path(Segment) { filePath =>
          log.debug(s"Hitting Get File by Url:${filePath}")
          val file = filesService.getFileByUrl(filePath.toString)
          file match {
            case None       => complete(StatusCodes.NoContent)
            case Some(file) => complete(file)
          }
        }
    } ~
      (post & pathEnd) {
        formFields("url", "file", "fileName", "fileSize", "fileTags", "fileType") { (url, fileContent, name, size, tags, fileType) =>
          log.debug("posting to create a File")

          val metadata = new Metadata(name, size, "admin", tags, fileType, Calendar.getInstance().getTime().toString)
          val file = new File(url, metadata, Option(fileContent.getBytes));

          val newFile = filesService.addFile(file)
          complete(newFile);
        }
      } ~
      (put & path(Segment) & pathEnd) { fileId =>
        entity(as[File]) { file =>
          log.debug(s"updating a File with the url: ${fileId}")
          val updatedFile = filesService.updateFile(file)
          updatedFile match {
            case true  => complete(StatusCodes.NoContent)
            case false => complete(StatusCodes.NotFound)
          }
        }
      }
  }
  private val filesService = FilesService
}
