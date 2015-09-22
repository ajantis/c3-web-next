package com.ifunsoftware.c3web.config

import akka.actor.ActorSystem
import com.ifunsoftware.c3web.routing.{ApiRouterActor, PingRoute}

/**
 * Created by Alexander on 9/22/2015.
 */

/**
 * Factory method for ActorSystemBean class
 */
object ActorSystemBean {
  def apply(): ActorSystemBean = new ActorSystemBean()
}

/**
 * Defines an actor system with the actors used by
 * the spray-person application
 */
class ActorSystemBean {

  lazy val personRoute = system.actorOf(PingRoute.props, "ping-route")
  lazy val apiRouterActor = system.actorOf(ApiRouterActor.props(personRoute), "api-router")
  implicit val system = ActorSystem("c3web-server")
}
