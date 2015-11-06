package com.ifunsoftware.c3web.service

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

  def getGroupByUUID(UUID: String): Option[Group] = {
    groupMock find (_.uid.contains(UUID))
  }

  def addGroup(group: Group): Group = {
    val maxId = groupMock.map(_.id).flatten.max + 1
    val newgroup = group.copy(id = Some(maxId))
    groupMock += newgroup
    newgroup
  }

  def updateGroup(group: Group): Boolean = {
    groupMock.indexWhere(_.id == group.id) match {
      case -1 => false
      case i => groupMock.update(i, group); true
    }
  }

  def deleteGroup(id: Int): Unit = {
    getGroupById(id) match {
      case Some(group) => groupMock -= group
      case None =>
    }
  }

  def getGroupById(groupId: Int): Option[Group] = {
    groupMock find (_.id == Some(groupId))
  }

}
