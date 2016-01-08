package com.ifunsoftware.c3web.service

import java.util.UUID

import com.ifunsoftware.c3web.data.UserData
import com.ifunsoftware.c3web.models.User

/**
 * Created by alexander on 01.11.15.
 */

/**
 * UserService manages state of users in a mutable array
 * for demo purposes.  In a full system this would call a DAO
 * layer.
 */
object UserService {

  import UserData.userMock

  def getUsers: List[User] = {
    userMock.toList
  }

  def getUserByEmail(userEmail: String): Option[User] = {
    userMock find (_.username == userEmail)
  }

  def addUser(user: User): User = {
    val maxId = UUID.randomUUID().toString
    val newUser = user.copy(id = maxId)
    userMock += newUser
    newUser
  }

  def updateUser(user: User): Boolean = {
    userMock.indexWhere(_.id == user.id) match {
      case -1 => false
      case i  => userMock.update(i, user); true
    }
  }

  def deleteUser(userUUID: String): Unit = {
    getUserById(userUUID) match {
      case Some(user) => userMock -= user
      case None       =>
    }
  }

  def getUserById(userUUID: String): Option[User] = {
    userMock find (_.id == userUUID)
  }

}