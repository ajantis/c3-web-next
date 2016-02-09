package com.ifunsoftware.c3web.config

import akka.actor.ActorSystem
import com.ifunsoftware.c3web.routing._
import com.ifunsoftware.c3web.routing.files._
import com.ifunsoftware.c3web.routing.groups.{ GroupsRoute, GroupRoute }
import com.ifunsoftware.c3web.routing.journal.JournalMessagesRoute

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

  //separate route actors for handling specific requests
  lazy val authRoute = system.actorOf(AuthRoute.props, "auth-route")
  lazy val accountingRoute = system.actorOf(AccountingRoute.props, "acc-route")
  lazy val groupsRoute = system.actorOf(GroupsRoute.props, "groups-route")
  lazy val groupRoute = system.actorOf(GroupRoute.props, "group-route")
  lazy val fileRoute = system.actorOf(FileRoute.props, "file-route")
  lazy val filesRoute = system.actorOf(FilesRoute.props, "files-route")
  lazy val downloadRoute = system.actorOf(DownloadRoute.props, "download-route")
  lazy val annotationRoute = system.actorOf(AnnotationRoute.props, "annotation-route")

  lazy val journalRoute = system.actorOf(JournalMessagesRoute.props, "journal-route")
  //root API route
  lazy val apiRouterActor = system.actorOf(ApiRouterActor.props(authRoute, accountingRoute, groupsRoute, groupRoute,
    filesRoute, fileRoute, downloadRoute, annotationRoute, journalRoute), "api-router")
  //System actor
  implicit val system = ActorSystem("c3web-server")
}
