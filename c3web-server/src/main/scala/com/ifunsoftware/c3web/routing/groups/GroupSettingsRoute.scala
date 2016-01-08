package com.ifunsoftware.c3web.routing.groups

import akka.actor.{ Actor, Props }
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Created by alexander on 05.12.15.
 */
object GroupSettingsRoute {
  def props: Props = Props(new GroupSettingsRoute())
}

/**
 * Actor that handles requests that begin with "file" (certain file)
 */
class GroupSettingsRoute() extends Actor with GroupSettingsRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(settringsRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait GroupSettingsRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[GroupSettingsRouteTrait])
  val settringsRoute = {
    get {
      pathEnd {
        complete(StatusCodes.NoContent)
      }
    }
  }
}