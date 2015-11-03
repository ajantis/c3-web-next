package com.ifunsoftware.c3web.data

import com.ifunsoftware.c3web.models.Group

import scala.collection.mutable.ArrayBuffer

/**
 * Created by alexander on 02.11.15.
 */

/** User data stored in a mutable array for demonstration purposes.
  * This would normally be replaced by a DAO layer that makes calls to
  * a database or external service that persists user data.
  */
object GroupData {
  val groupMock = ArrayBuffer(
    Group(Some(1), Some("Perfomance"), Some("47259a7c-826d-11e5-8bcf-feff819cdc9f"), Some("Description")),
    Group(Some(2), Some("C3-Docs"), Some("47259f72-826d-11e5-8bcf-feff819cdc9f"), Some("Test description")),
    Group(Some(3), Some("DevOPS"), Some("4725a06c-826d-11e5-8bcf-feff819cdc9f"), Some("Nice group")))
}
