package com.ifunsoftware.c3web.service

import java.util.UUID

import com.ifunsoftware.c3web.data.{ FileData, GroupData }
import com.ifunsoftware.c3web.data.GroupData._
import com.ifunsoftware.c3web.models.{ File, Group }

/**
 * Created by admin on 23.01.2016.
 */

object FilesService {

  import FileData.fileMock

  def getFiles(groupId: String): List[File] = {
    (fileMock filter (_.url.contains(groupId))).toList
  }

  def getFileByUrl(url: String): Option[File] = {
    fileMock find (_.url.equalsIgnoreCase(url))
  }

  def getVocabulary(groupId: String): Option[File] = {
    (fileMock find (_.url.contains(".cv")))
  }

  def addFile(file: File): File = {
    fileMock += file
    file
  }

  def updateFile(file: File): Boolean = {
    fileMock.indexWhere(_.url == file.url) match {
      case -1 => false
      case i  => fileMock.update(i, file); true
    }
  }

  def deleteFile(url: String): Unit = {
    getFileByUrl(url) match {
      case Some(file) => fileMock -= file
      case None       =>
    }
  }
}

