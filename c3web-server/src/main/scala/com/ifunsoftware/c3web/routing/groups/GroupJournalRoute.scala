package com.ifunsoftware.c3web.routing.groups

import akka.actor.{ Actor, Props }
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Created by alexander on 05.12.15.
 */
object GroupJournalRoute {
  def props: Props = Props(new GroupJournalRoute())
}

/**
 * Actor that handles requests that begin with "file" (certain file)
 */
class GroupJournalRoute() extends Actor with GroupJournalRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(journalRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait GroupJournalRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[GroupJournalRouteTrait])
  val journalRoute = {
    get {
      pathEnd {
        complete(StatusCodes.NoContent)
      }
    }
  }
}
