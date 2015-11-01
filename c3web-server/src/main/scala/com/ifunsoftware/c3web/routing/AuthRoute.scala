package com.ifunsoftware.c3web.routing

/**
 * Created by alexander on 9/22/2015.
 */

import akka.actor.{Actor, Props}
import com.ifunsoftware.c3web.models.AuthInfo
import com.ifunsoftware.c3web.models.AuthInfoEntryJson._
import com.ifunsoftware.c3web.service.AuthenticationService
import org.slf4j.LoggerFactory
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
 * Actor that handles requests that begin with "auth" (authentication)
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

  val log = LoggerFactory.getLogger(classOf[AuthRouteTrait])
  val authRoute = {
    get {
      pathEnd {
        complete {
          log.debug("Hitting to GET for auth path")
          StatusCodes.NotAcceptable
        }
      }
    } ~
      (post & pathEnd) {
        log.debug("Hitting to POST for auth path")
        decompressRequest() {
          entity(as[AuthInfo]) { authInfo =>
            log.debug(s"Trying to authenticate user:${authInfo.username}")
            val authinfoupdated = authService.authenticateUser(authInfo)
            authinfoupdated match {
              case Some(authinfoupdated) => complete(StatusCodes.OK, authinfoupdated)
              case None => complete(StatusCodes.NotFound)
            }
          }
        }
      }
  }
  private val authService = AuthenticationService
}