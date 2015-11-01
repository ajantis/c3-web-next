package com.ifunsoftware.c3web.routing

/**
 * Created by Alexander on 9/22/2015.
 */

import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models.{AuthResponse, User}
import spray.http.StatusCodes
import com.ifunsoftware.c3web.models.UserEntryJson._
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object AuthRoute {
  def props: Props = Props(new AuthRoute())
}

/**
 * Actor that handles requests that begin with "person"
 */
class AuthRoute() extends Actor with AuthRouteTrait {
  def actorRefFactory = context
  def receive = runRoute(authRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait AuthRouteTrait extends HttpService with SprayJsonSupport {

  val authRoute = {
    get {
      pathEnd {
        complete {
          StatusCodes.NotAcceptable
        }
      }
    } ~
      (post & pathEnd) {
        decompressRequest() {
          entity(as[User]) { user =>
            if(user.username.equals("admin@admin.com") && user.password.equals("admin")){
              val authResponse =  AuthResponse (1,1,"token")
              val authenticatedUser = new User(user.id, user.username, user.password, "tokenadm")
              complete(authenticatedUser)
            }
            else if(user.username.equals("user@user.com") && user.password.equals("user")){
              val authenticatedUser = new User(user.id, user.username, user.password, "tokenuser")
              complete(user)}
            else
              complete(StatusCodes.NotFound)
          }
        }
      }
  }
}