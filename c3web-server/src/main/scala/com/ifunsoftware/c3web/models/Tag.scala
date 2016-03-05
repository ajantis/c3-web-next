package com.ifunsoftware.c3web.models

import spray.json.DefaultJsonProtocol

/**
 * Created by admin on 09.02.2016.
 */
object TagEntryJson extends DefaultJsonProtocol {
  implicit val tagResponseFormat = jsonFormat1(Tag.apply)
}

case class Tag(text: String) {

}