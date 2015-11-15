package com.ifunsoftware.c3web.routing.files

import akka.actor.{Actor, Props}
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
      }
    }
  }
}
