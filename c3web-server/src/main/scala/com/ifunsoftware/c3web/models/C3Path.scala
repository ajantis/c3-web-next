package com.ifunsoftware.c3web.models

/**
  * Created by alexander on 19.12.15.
  */

case class C3Path(path: String) {

  var groupName: String = ""

  var resourceName: String = ""

  var resourceType: ResourceType = UnknownType

  var resourceUri = ""

  var resourceParentDir = ""

  {
    path.split("/").toList.filter(!_.isEmpty) match {
      case fullpath @ (group :: "files" :: filePath) => {
        groupName = group
        resourceName = filePath.last
        resourceUri = "/groups/" + group + "/files/" + filePath.mkString("/")
        var resourceLink = filePath.filter(_ != resourceName).mkString("/")
        if (!resourceLink.isEmpty) {
          resourceLink += '/'
        }
        resourceType = FileType
        resourceParentDir = "/groups/" + group + "/files/" + resourceLink
      }

      case fullpath @ (group :: "wiki" :: filePath) => {
        groupName = group
        resourceName = filePath.head
        resourceType = WikiType
        resourceUri = "/groups/" + group + "/wiki/" + resourceName
      }
      case fullpath @ (group :: "messages" :: filePath) => {
        groupName = group
        resourceName = "Message"
        resourceType = MessagesType
        resourceUri = "/groups/" + group + "/messages"
        resourceParentDir = "/groups/" + group + "/messages" + "#" + filePath.last
      }

      case _ =>
    }
  }

}

object C3Path {

  def apply(groupId: String, path: List[String], extension: String): String = {
    "/" + groupId + "/" + path.init.mkString("/") + "/" +
      path.last + {
      extension match {
        case ""  => ""
        case ext => "." + ext
      }
    }
  }
}

sealed trait ResourceType
object FileType extends ResourceType
object WikiType extends ResourceType
object UnknownType extends ResourceType
object MessagesType extends ResourceType