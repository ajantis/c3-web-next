package com.ifunsoftware.c3web.data

import com.ifunsoftware.c3web.models.JournalMessage
import scala.collection.mutable.ArrayBuffer

/**
 * Created by alexander on 1/8/2016.
 */

object JournalMessagesData {
  import GroupData.groupMock
  import UserData.userMock

  val messagesMock = ArrayBuffer(
    JournalMessage(groupMock(0).id, userMock(0).id, "first message", "e471784c-dbac-4f56-85b4-34cb5826c8dd", List.empty, None),
    JournalMessage(groupMock(0).id, userMock(1).id, "second message same group", "3bc4bbdb-02b2-466b-a914-6be488ac0cf2", List.empty, None),
    JournalMessage(groupMock(1).id, userMock(1).id, "second message", "217ddfe9-6d02-4e9d-82b1-1bbc45f40382", List.empty, None),
    JournalMessage(groupMock(2).id, userMock(2).id, "third message", "fc2679dd-ae23-488b-8c87-94684c2c5432", List.empty, None))
}
