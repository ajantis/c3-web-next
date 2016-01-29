package com.ifunsoftware.c3web.models

import spray.http.DateTime
import spray.json.DefaultJsonProtocol

/**
 * Created by admin on 29.01.2016.
 */

object MetadataEntryJson extends DefaultJsonProtocol {
  implicit val metadataResponseFormat = jsonFormat6(Metadata.apply)
}

case class Metadata(title: String, size: String, owner: String, tags: String, documentType: String,
                    creationTime: String) {

  override def toString = {
    val builder = new StringBuilder("Metadata{")
    builder.append("title=").append(title).
      append(", size=").append(size).
      append(", owner=").append(owner).
      append(", tags= ").append(tags).
      append(", documentType= ").append(documentType).
      append(", creationTime= ").append(creationTime).
      append("}")
    builder.toString()
  }
}