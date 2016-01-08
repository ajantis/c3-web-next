package com.ifunsoftware.c3web.models

/**
 * Created by alexander on 01.11.15.
 */

import spray.json._

object GroupEntryJson extends DefaultJsonProtocol {
  implicit val groupResponseFormat = jsonFormat3(Group.apply)
}

case class Group(id: String, groupname: Option[String], description: Option[String]) {
}