package com.ifunsoftware.c3web.models

/**
 * Created by alexander on 24.10.15.
 */

import spray.json._

object UserEntryJson extends DefaultJsonProtocol {
  implicit val userFormat= jsonFormat4(User.apply)
}

case class User(id: Int, username: String, password: String, token: String){
}
