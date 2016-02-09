package com.ifunsoftware.c3web.models

import spray.json.DefaultJsonProtocol

/**
 * Created by admin on 09.02.2016.
 */
object KeywordEntryJson extends DefaultJsonProtocol {
  implicit val keywordResponseFormat = jsonFormat1(Keyword.apply)
}

case class Keyword(text: String) {

}