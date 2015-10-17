package com.ifunsoftware.c3web.routing

/**
 * Created by Alexander on 9/22/2015.
 */

import akka.actor.{ Actor, Props }
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object PingRoute {
  def props: Props = Props(new PingRoute())
}

/**
 * Actor that handles requests that begin with "person"
 */
class PingRoute() extends Actor with PingRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(pingRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait PingRouteTrait extends HttpService with SprayJsonSupport {

  val pingRoute = {
    get {
      pathEnd {
        complete("pong")
      }
    }
  }
}