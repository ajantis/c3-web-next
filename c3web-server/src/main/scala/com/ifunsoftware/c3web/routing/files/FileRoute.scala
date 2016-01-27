package com.ifunsoftware.c3web.routing.files

import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models.File
import com.ifunsoftware.c3web.models.FileEntryJson._
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
        entity(as[File]) { file =>
          log.debug("posting to create a File")
          val newFile = filesService.addFile(file)
          complete(newFile)
        }
      } ~
      //TODO fix
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
