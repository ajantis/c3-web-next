/**
 * Copyright (c) 2015, Dmitry Ivanov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the IFMO nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.ifunsoftware.c3web

import java.util.UUID

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer

import akka.http.scaladsl.Http
import akka.pattern.ask
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.util.Timeout
import com.ifunsoftware.c3web.auth.AuthenticationProvider.Authenticate
import com.ifunsoftware.c3web.auth.{ AuthenticationProvider, UserPassCredentials }
import com.ifunsoftware.c3web.users.UsersStore
import com.ifunsoftware.c3web.users.UsersStore.FetchUserById
import com.softwaremill.session.{ SessionSerializer, SessionManager, SessionConfig }
import com.softwaremill.session.SessionDirectives._
import com.typesafe.config.ConfigException

import spray.json._
import DefaultJsonProtocol._
import com.ifunsoftware.c3web.domain.User

/**
 * Main entry-point of the server application that instantiates all components.
 */
object Boot extends App {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val logger = Logging(system, getClass)

  private val settings =
    try {
      // Eagerly load settings to fail-fast if something is missing
      Settings(system)
    } catch {
      case e: ConfigException =>
        println("[ERROR] Configuration is invalid: ")
        println()
        println(s"Details: ${e.getMessage}")
        println()

        System.exit(-1)
        null
    }

  private val httpSettings = settings.Http

  implicit val futureTimeout = Timeout(settings.Timeouts.DefaultFutureTimeout)

  val routes = {
    logRequestResult("c3web-service") {
      apiRoute ~ staticRoute
    }
  }

  implicit def stringSessionSerializer = new SessionSerializer[UUID] {
    override def serialize(t: UUID): String = t.toString
    override def deserialize(s: String): UUID = UUID.fromString(s)
  }

  val sessionConfig = SessionConfig.default("secret_password") // TODO move to settings
  implicit val sessionManager = new SessionManager[UUID](sessionConfig)

  val usersStore = system.actorOf(UsersStore.props())
  val authProvider = system.actorOf(AuthenticationProvider.props(usersStore))

  /*
   * This route serves all API requests.
   */
  def apiRoute: Route = {
    path("ping") {
      complete(OK -> "pong")
    } ~
      path("login") {
        post {
          entity(as[UserPassCredentials]) { credentials =>

            onSuccess((authProvider ? Authenticate(credentials)).mapTo[Option[User]]) {
              case Some(user) =>
                setSession(UUID.randomUUID()) {
                  println("AAA good credentials")
                  complete("OK")
                }

              case _ =>
                println("AAA bad credentials")
                complete(StatusCodes.Unauthorized, "Bad credentials.")
            }
          }
        }
      } ~
      pathPrefix("api") {
        requiredSession[UUID]() { session =>
          path("users" / JavaUUID) { userId =>
            get {
              onSuccess((usersStore ? FetchUserById(userId)).mapTo[Option[User]]) { userOption =>
                userOption.map(user => complete(OK -> user)).getOrElse(complete(NotFound))
              }
            }
          }
        }
      }
  }

  /*
   * This route serves all static content (i.e. HTML, CSS, JS).
   */
  def staticRoute: Route =
    path("") {
      getFromResource("web/index.html")
    } ~ getFromResourceDirectory("web")

  Http().bindAndHandle(routes, httpSettings.Interface, httpSettings.Port)
}
