package com.ifunsoftware.c3web.domain

import User._
import spray.json.{ DeserializationException, JsString, JsValue, JsonFormat }

import scala.util.Try

case class User(id: Id, email: Email)

object User {
  import spray.json._
  import DefaultJsonProtocol._
  import java.util.UUID

  type Id = UUID
  type Email = String

  implicit val uuidFormat = new JsonFormat[Id] {
    override def read(json: JsValue): Id = {
      json match {
        case JsString(v) if Try(UUID.fromString(v)).isSuccess => UUID.fromString(v)
        case _ => throw new DeserializationException(s"Failed to parse UUID from json value '$json'")
      }
    }

    override def write(id: Id): JsValue = JsString(id.toString)
  }

  implicit val format = jsonFormat2(User.apply)
}
