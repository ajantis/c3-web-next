package com.ifunsoftware.c3web.routing.groups

/**
 * Created by alexander on 9/22/2015.
 */

import akka.actor.{Actor, Props}
import com.ifunsoftware.c3web.service.GroupService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import com.ifunsoftware.c3web.models.GroupEntryJson._
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object GroupsRoute {
  def props: Props = Props(new GroupsRoute())
}

/**
 * Actor that handles requests that begin with "groups" (groups)
 */
class GroupsRoute() extends Actor with GroupsRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(groupsRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait GroupsRouteTrait extends HttpService with SprayJsonSupport {
  val log = LoggerFactory.getLogger(classOf[GroupsRouteTrait])
  val groupsRoute = {
    get {
      pathEnd {
        complete {
          log.debug("Hitting to GET for GROUPS path")
          val groups = groupService.getGroups
          groups match {
            case head :: tail => groups
            case Nil => StatusCodes.NoContent
          }
        }
      }
    }
  }
  private val groupService = GroupService
}