package com.ifunsoftware.c3web.models

/**
  * Created by alexander on 1/8/2016.
  */

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import spray.json._

object JournalMessageEntryJson extends DefaultJsonProtocol {
  implicit val messageFormat = jsonFormat6(JournalMessage.apply)
}

case class JournalMessage(groupId: String, authorId: String, content: String, id: String,
                   tags: List[String], parent: Option[String] = None) {

  override def toString = {
    val builder = new StringBuilder("Message{")
    builder.append("authorId=").append(authorId).
      append(", groupId=").append(groupId).
      append(", content=").append(content).
      append(", parent = ").append(parent).
      append("}")
    builder.toString()
}
}