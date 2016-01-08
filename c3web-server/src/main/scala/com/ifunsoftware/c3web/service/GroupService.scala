package com.ifunsoftware.c3web.service

import java.util.UUID

import com.ifunsoftware.c3web.data.GroupData
import com.ifunsoftware.c3web.models.Group

/**
 * Created by alexander on 03.11.15.
 */
/**
 * UserService manages state of users in a mutable array
 * for demo purposes.  In a full system this would call a DAO
 * layer.
 */
object GroupService {

  import GroupData.groupMock

  def getGroups: List[Group] = {
    groupMock.toList
  }

  def getGroupById(UUID: String): Option[Group] = {
    groupMock find (_.id.contains(UUID))
  }

  def addGroup(group: Group): Group = {
    val maxId = UUID.randomUUID()
    val newgroup = group.copy(id = maxId.toString)
    groupMock += newgroup
    newgroup
  }

  def updateGroup(group: Group): Boolean = {
    groupMock.indexWhere(_.id == group.id) match {
      case -1 => false
      case i  => groupMock.update(i, group); true
    }
  }

  def deleteGroup(UUID: String): Unit = {
    getGroupById(UUID) match {
      case Some(group) => groupMock -= group
      case None        =>
    }
  }
}
