package com.ifunsoftware.c3web.service

import java.util.UUID

import com.ifunsoftware.c3web.data.{ JournalMessagesData }
import com.ifunsoftware.c3web.models.{ JournalMessage }
import spray.routing.RequestContext

/**
 * Created by alexander on 1/8/2016.
 */
object JournalMessagesService {
  import JournalMessagesData.messagesMock

  def getMessages: List[JournalMessage] = {
    messagesMock.toList
  }

  def addMessage(message: JournalMessage): JournalMessage = {
    val maxId = UUID.randomUUID();
    val newmessage = message.copy(id = maxId.toString)
    messagesMock += newmessage
    newmessage
  }

  def updateMessage(message: JournalMessage): Boolean = {
    messagesMock.indexWhere(_.id == message.id) match {
      case -1 => false
      case i  => messagesMock.update(i, message); true
    }
  }

  def deleteMessage(id: String): Unit = {
    getMessageById(id) match {
      case Some(user) => messagesMock -= user
      case None       =>
    }
  }

  def getMessageById(id: String): Option[JournalMessage] = {
    messagesMock find (_.id == id)
  }
}
