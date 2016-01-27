package com.ifunsoftware.c3web.routing

/**
 * Created by alexander on 9/21/2015.
 */

import akka.actor.{ Actor, ActorLogging, ActorRef, Props }
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object ApiRouterActor {
  def props(authRoute: ActorRef, accRoute: ActorRef, groupsRoute: ActorRef, groupRoute: ActorRef, filesRoute: ActorRef, fileRoute: ActorRef, journalRoute: ActorRef): Props =
    Props(new ApiRouterActor(authRoute, accRoute, groupsRoute, groupRoute, filesRoute, fileRoute, journalRoute))
}

/**
 * Routes the incoming request.  If the route begins with "api" the request is passed
 * along to the matching spray routing actor (if there's a match)
 */
class ApiRouterActor(authRoute: ActorRef, accRoute: ActorRef, groupsRoute: ActorRef, groupRoute: ActorRef, filesRoute: ActorRef, fileRoute: ActorRef, journalRoute: ActorRef) extends Actor
  with HttpService
  with ActorLogging {

  def actorRefFactory = context
  def receive = runRoute {
    compressResponseIfRequested() {
      pathPrefix("api") {
        log.debug("Handling API request")
        pathPrefix("auth") { ctx => authRoute ! ctx } ~
          pathPrefix("user") { ctx => accRoute ! ctx } ~
          pathPrefix("groups") { ctx => groupsRoute ! ctx } ~
          pathPrefix("group") { ctx => groupRoute ! ctx } ~
          pathPrefix("journal") { ctx => journalRoute ! ctx } ~
          pathPrefix("files") { ctx => filesRoute ! ctx }
      } ~
        {
          path("") {
            getFromResource("web/index.html")
          } ~ getFromResourceDirectory("web")
        }
    }
  }
}