package com.ifunsoftware.c3web.models

/**
 * Created by alexander on 01.11.15.
 */

import spray.json._

object GroupEntryJson extends DefaultJsonProtocol {
  implicit val authResponseFormat = jsonFormat2(Group.apply)
}

case class Group(id: Option[Int], groupname: String)