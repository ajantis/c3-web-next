package com.ifunsoftware.c3web.routing.files

import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.service.FilesService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService
import com.ifunsoftware.c3web.models.FileEntryJson._

/**
 * Created by admin on 27.01.2016.
 */

object FilesRoute {
  def props: Props = Props(new FilesRoute())
}

/**
 * Actor that handles requests that begin with "groups" (groups)
 */
class FilesRoute() extends Actor with FilesRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(filesRoute)
}

trait FilesRouteTrait extends HttpService with SprayJsonSupport {
  val log = LoggerFactory.getLogger(classOf[FilesRouteTrait])
  val filesRoute = {
    get {
      pathEnd {
        complete {
          log.debug("Hitting to GET for Files path")
          val files = filesService.getFiles
          log.debug("Files got  ")
          files match {
            case head :: tail => files
            case Nil          => StatusCodes.NoContent
          }
        }
      }
    }
  }
  private val filesService = FilesService
}