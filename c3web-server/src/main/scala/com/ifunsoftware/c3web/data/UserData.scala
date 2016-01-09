package com.ifunsoftware.c3web.data

import com.ifunsoftware.c3web.models.User

import scala.collection.mutable.ArrayBuffer

/**
 * Created by alexander on 01.11.15.
 */

/**
 * S
 * User data stored in a mutable array for demonstration purposes.
 * This would normally be replaced by a DAO layer that makes calls to
 * a database or external service that persists user data.
 */
object UserData {
  val userMock = ArrayBuffer(
    User("b88f00cf-6886-4abf-9799-3903bca79827", "admin@admin.com", "admin"),
    User("e879ec5a-0ae8-4e2b-813f-50a66036c5ca", "user@user.com", "user"),
    User("4d50ca40-49bb-4c10-9317-45f32cf8a944", "test@test.com", "test"))
}

