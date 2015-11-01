package com.ifunsoftware.c3web.routing

/**
 * Created by alexander on 9/21/2015.
 */

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object ApiRouterActor {
  def props(authRoute: ActorRef, accRoute: ActorRef, groupRoute: ActorRef): Props = Props(new ApiRouterActor(authRoute, accRoute, groupRoute))
}

/**
 * Routes the incoming request.  If the route begins with "api" the request is passed
 * along to the matching spray routing actor (if there's a match)
 */
class ApiRouterActor(authRoute: ActorRef, accRoute: ActorRef, groupRoute: ActorRef) extends Actor
  with HttpService
  with ActorLogging {

  def actorRefFactory = context

  def receive = runRoute {
    compressResponseIfRequested() {
      pathPrefix("api") {
        pathPrefix("auth") { ctx => authRoute ! ctx } ~
          pathPrefix("user") { ctx => accRoute ! ctx } ~
          pathPrefix("groups") { ctx => groupRoute ! ctx }
      } ~
        {
          path("") {
            getFromResource("web/index.html")
          } ~ getFromResourceDirectory("web")
        }
    }
  }
}