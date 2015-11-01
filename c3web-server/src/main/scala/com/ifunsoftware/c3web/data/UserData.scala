package com.ifunsoftware.c3web.data

import com.ifunsoftware.c3web.models.User

import scala.collection.mutable.ArrayBuffer

/**
 * Created by alexander on 01.11.15.
 */

/** S
  * User data stored in a mutable array for demonstration purposes.
  * This would normally be replaced by a DAO layer that makes calls to
  * a database or external service that persists user data.
  */
object UserData {
  val userMock = ArrayBuffer(
    User(Some(1), "admin@admin.com", "admin"),
    User(Some(2), "user@user.com", "user"),
    User(Some(3), "test@test.com", "test"))
}