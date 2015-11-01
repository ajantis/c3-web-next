package com.ifunsoftware.c3web.models

/**
 * Created by alexander on 01.11.15.
 */

import spray.json._

object AuthResponseEntryJson extends DefaultJsonProtocol {
  implicit val userFormat= jsonFormat3(AuthResponse.apply)
}

case class AuthResponse(id: Int, userId: Int, api_token: String)

