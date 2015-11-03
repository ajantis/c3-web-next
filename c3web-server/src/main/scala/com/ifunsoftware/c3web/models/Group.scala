package com.ifunsoftware.c3web.models

/**
 * Created by alexander on 01.11.15.
 */

import spray.json._

object GroupEntryJson extends DefaultJsonProtocol {
  implicit val groupResponseFormat = jsonFormat4(Group.apply)
}

case class Group(id: Option[Int], groupname: Option[String], uid: Option[String], description: Option[String]) {
}