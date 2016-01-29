package com.ifunsoftware.c3web.models

/**
 * Created by admin on 24.01.2016.
 */
import spray.json._
import com.ifunsoftware.c3web.models.MetadataEntryJson._

object FileEntryJson extends DefaultJsonProtocol {
  implicit val fileResponseFormat = jsonFormat3(File.apply)
}

case class File(url: String, metadata: Metadata, content: Option[Array[Byte]]) {
}