package test.services

/**
 * Created by alexander on 22.11.15.
 */

import akka.actor.ActorRefFactory
import com.ifunsoftware.c3web.data.UserData
import com.ifunsoftware.c3web.data.UserData._
import com.ifunsoftware.c3web.models.User
import com.ifunsoftware.c3web.routing.{ AccountingRoute, AccountingRouteTrait }
import spray.http.StatusCodes
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.specs2.mutable.Specification
import spray.testkit.{ Specs2RouteTest, ScalatestRouteTest }
import com.ifunsoftware.c3web.models.UserEntryJson._
import spray.httpx.SprayJsonSupport._
import spray.httpx.unmarshalling._

class UserServiceSpec extends Specification with Specs2RouteTest with AccountingRouteTrait {

  def actorRefFactory = system
  import UserData.userMock

  val userID = "e879ec5a-0ae8-4e2b-813f-50a66036c5ca"
  implicit def executionContext = actorRefFactory.dispatcher

  "The Accounting (User) route" should {

    "return a user for GET requests to the UUID path" in {
      Get("/" + userID) ~> accRoute ~> check {
        status equals StatusCodes.OK
        val user = userMock find (_.id == userID)
        handled must beTrue
        response.entity should not be equalTo(None)
      }
    }
    "leave GET requests to other paths unhandled" in {
      Get("/blahblah") ~> accRoute ~> check {
        handled must beFalse
      }
    }
    "return a user for GET requests to the root path" in {
      Get() ~> accRoute ~> check {
        status equals StatusCodes.OK
        handled must beTrue
        response.entity should not be equalTo(None)
      }
    }
  }
}