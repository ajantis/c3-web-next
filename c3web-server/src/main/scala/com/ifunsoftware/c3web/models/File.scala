package com.ifunsoftware.c3web.models

/**
 * Created by admin on 24.01.2016.
 */

import spray.http.MediaType
import spray.json._
import com.ifunsoftware.c3web.models.MetadataEntryJson._
import spray.httpx.SprayJsonSupport._
import spray.http.MediaTypes._

object FileEntryJson extends DefaultJsonProtocol {
  implicit val fileResponseFormat = jsonFormat5(File.apply)
}

case class File(url: String, metadata: Metadata, content: Option[Array[Byte]], contentType: String, isFolder: Boolean) {
}