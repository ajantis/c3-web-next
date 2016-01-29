package com.ifunsoftware.c3web.data
import com.ifunsoftware.c3web.models.{ File, Group, Metadata }

import scala.collection.mutable.ArrayBuffer

/**
 * Created by admin on 23.01.2016.
 */
object FileData {
  val fileMock = ArrayBuffer(
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file1.txt",
      new Metadata("file1", "10241024", "admin", "tag1", "other", "23.12.2015"),
      None),
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file2.txt",
      new Metadata("file2", "2048234234", "admin", "tag1", "other", "24.12.2015"), None),
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file3.txt",
      new Metadata("file3", "34534523", "admin", "tag1", "other", "25.12.2015"), None),
    File("/005a47bb-5457-4c22-ba27-4d61608f5a37/file3.txt",
      new Metadata("file4", "1231233", "admin", "tag1", "other", "23.12.2015"), None),
    File("/273a4a8c-40c8-437d-8e58-9c56dc5f48dd/file3.txt",
      new Metadata("file5", "10241024", "admin", "tag1", "other", "23.12.2015"), None))
}
