package com.ifunsoftware.c3web.routing.journal

/**
  * Created by alexander on 1/8/2016.
  */
import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models.JournalMessage
import com.ifunsoftware.c3web.models.JournalMessageEntryJson._
import com.ifunsoftware.c3web.service.JournalMessagesService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
  * Factory method for Props configuration files for actors
  */
object JournalMessagesRoute {
  def props: Props = Props(new JournalMessagesRoute())
}

/**
  * Actor that handles requests that begin with "user" (accounting)
  */
class JournalMessagesRoute() extends Actor with JournalMessagesRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(journalMessagesRoute)
}

/**
  * Separate routing logic in an HttpService trait so that the
  * routing logic can be tested outside of an actor system in specs/mockito tests
  */
trait JournalMessagesRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[JournalMessagesRoute])
  val journalMessagesRoute = {
    get {
      pathEnd {
        complete {
          log.debug("Hitting Get All Messages")
          val messages = journalMessagesService.getMessages
          messages match {
            case head :: tail => messages
            case Nil          => StatusCodes.NoContent
          }
        }
      } ~
        path(JavaUUID) { messageId =>
          log.debug(s"Hitting Get Message by Id:${messageId}")
          val message = journalMessagesService.getMessageById(messageId.toString)
          complete(message)
        }
    } ~
      (post & pathEnd) {
        entity(as[JournalMessage]) { message =>
          log.debug("posting to create a message")
          val newMessage = journalMessagesService.addMessage(message)
          complete(newMessage)
        }
      } ~
      (put & path(JavaUUID) & pathEnd) { messageId =>
        entity(as[JournalMessage]) { message =>
          log.debug(s"updating a message with the id: ${messageId}")
          val updatedMessage = journalMessagesService.updateMessage(message.copy(id = messageId.toString))
          updatedMessage match {
            case true  => complete(StatusCodes.NoContent)
            case false => complete(StatusCodes.NotFound)
          }
        }
      } ~
      (delete & path(JavaUUID) & pathEnd) { messageId =>
        log.debug(s"deleting a message with the id: ${messageId}")
        journalMessagesService.deleteMessage(messageId.toString)
        complete(StatusCodes.NoContent)
      }
  }
  private val journalMessagesService = JournalMessagesService
}
