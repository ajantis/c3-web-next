package com.ifunsoftware.c3web.models

/**
 * Created by alexander on 01.11.15.
 */

import spray.json._

object AuthInfoEntryJson extends DefaultJsonProtocol {
  implicit val authResponseFormat = jsonFormat4(AuthInfo.apply)
}

case class AuthInfo(id: Option[Int], username: String, password: String, api_token: Option[String])

