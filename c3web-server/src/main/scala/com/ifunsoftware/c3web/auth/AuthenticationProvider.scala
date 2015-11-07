package com.ifunsoftware.c3web.auth

import akka.actor.{ ActorRef, Props, Actor }
import akka.pattern.{ ask, pipe }
import akka.util.Timeout
import com.ifunsoftware.c3web.Settings
import com.ifunsoftware.c3web.domain.User
import com.ifunsoftware.c3web.users.UsersStore.FetchUserByEmail
import spray.json.DefaultJsonProtocol._

class AuthenticationProvider(usersStore: ActorRef) extends Actor {
  import AuthenticationProvider._

  implicit val dispatcher = context.dispatcher

  implicit val timeout = Timeout(Settings(context.system).Timeouts.DefaultFutureTimeout)

  val userAccounts = Seq(
    UserPassCredentials("user1@test.com", "pass1"),
    UserPassCredentials("user2@test.com", "pass2"),
    UserPassCredentials("user3@test.com", "pass3"))

  def receive = {
    case Authenticate(credentials) => tryAuthenticate(credentials, sender())
  }

  private def tryAuthenticate(credentials: UserPassCredentials, requester: ActorRef): Unit = {
    val (user, pass) = (credentials.user, credentials.password)
    val accountOpt = userAccounts.find(account => account.user == user && account.password == pass)

    accountOpt.map { account =>
      (usersStore ? FetchUserByEmail(account.user)).mapTo[Option[User]].pipeTo(requester)
    }.getOrElse {
      requester ! None
    }
  }
}

object AuthenticationProvider {

  case class Authenticate(credentials: UserPassCredentials)

  def props(usersStore: ActorRef): Props = Props(new AuthenticationProvider(usersStore))
}

sealed trait Credentials
case class UserPassCredentials(user: String, password: String)

object UserPassCredentials {
  import spray.json._
  implicit val jsonFormat = jsonFormat2(UserPassCredentials.apply)
}
