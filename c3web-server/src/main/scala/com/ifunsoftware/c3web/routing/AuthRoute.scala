package com.ifunsoftware.c3web.routing

/**
 * Created by Alexander on 9/22/2015.
 */

import akka.actor.{Actor, Props}
import com.ifunsoftware.c3web.models.AuthInfo
import com.ifunsoftware.c3web.models.AuthInfoEntryJson._
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object AuthRoute {
  def props: Props = Props(new AuthRoute())
}

/**
 * Actor that handles requests that begin with "authentication"
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
          entity(as[AuthInfo]) { user =>
            if(user.username.equals("admin@admin.com") && user.password.equals("admin")){
              val authResponse = AuthInfo(Some(1), user.username, "", Some("tokenadm"))
              complete(StatusCodes.OK, authResponse)
            }
            else if(user.username.equals("user@user.com") && user.password.equals("user")){
              val authResponse = new AuthInfo(Some(2), user.username, "", Some("tokenuser"))
              complete(StatusCodes.OK, authResponse)
            }
            else
              complete(StatusCodes.NotFound)
          }
        }
      }
  }
}