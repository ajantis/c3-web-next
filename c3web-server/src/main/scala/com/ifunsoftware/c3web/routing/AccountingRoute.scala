package com.ifunsoftware.c3web.routing

/**
 * Created by alexander on 01.11.15.
 */

import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models.User
import com.ifunsoftware.c3web.models.UserEntryJson._
import com.ifunsoftware.c3web.service.UserService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * Factory method for Props configuration files for actors
 */
object AccountingRoute {
  def props: Props = Props(new AccountingRoute())
}

/**
 * Actor that handles requests that begin with "user" (accounting)
 */
class AccountingRoute() extends Actor with AccountingRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(accRoute)
}

/**
 * Separate routing logic in an HttpService trait so that the
 * routing logic can be tested outside of an actor system in specs/mockito tests
 */
trait AccountingRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[AccountingRouteTrait])
  val accRoute = {
    get {
      pathSingleSlash {
        complete {
          log.debug("Hitting Get All Users")
          val users = userService.getUsers
          users match {
            case head :: tail => users
            case Nil          => StatusCodes.NoContent
          }
        }
      } ~
        path(JavaUUID) { userId =>
          log.debug(s"Hitting Get User by Id:${userId}")
          val user = userService.getUserById(userId.toString)
          complete(user)
        }
    } ~
      (post & pathEnd) {
        entity(as[User]) { user =>
          log.debug("posting to create a user")
          val newUser = userService.addUser(user)
          complete(newUser)
        }
      } ~
      (put & path(JavaUUID) & pathEnd) { userId =>
        entity(as[User]) { user =>
          log.debug(s"updating a person with the id: ${userId}")
          val updatedUser = userService.updateUser(user.copy(id = userId.toString))
          updatedUser match {
            case true  => complete(StatusCodes.NoContent)
            case false => complete(StatusCodes.NotFound)
          }
        }
      } ~
      (delete & path(JavaUUID) & pathEnd) { userId =>
        log.debug(s"deleting a person with the id: ${userId}")
        userService.deleteUser(userId.toString)
        complete(StatusCodes.NoContent)
      }
  }
  private val userService = UserService
}