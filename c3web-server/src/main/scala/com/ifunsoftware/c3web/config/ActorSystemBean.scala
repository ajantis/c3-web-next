package com.ifunsoftware.c3web.config

import akka.actor.ActorSystem
import com.ifunsoftware.c3web.routing.{AccountingRoute, ApiRouterActor, AuthRoute, GroupsRoute}

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

  //separete route actors to handle specific requests
  lazy val authRoute = system.actorOf(AuthRoute.props, "auth-route")
  lazy val accountingRoute = system.actorOf(AccountingRoute.props, "acc-route")
  lazy val groupsRoute = system.actorOf(GroupsRoute.props, "groups-route")
  //root API route
  lazy val apiRouterActor = system.actorOf(ApiRouterActor.props(authRoute, accountingRoute, groupsRoute), "api-router")
  //System actor
  implicit val system = ActorSystem("c3web-server")
}
