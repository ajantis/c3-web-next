package com.ifunsoftware.c3web.data

import com.ifunsoftware.c3web.models.{ File, Group }

import scala.collection.mutable.ArrayBuffer

/**
 * Created by admin on 23.01.2016.
 */
object FileData {
  val fileMock = ArrayBuffer(
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file1.txt", "TEST1", None),
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file2.txt", "TEST2", None),
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file3.txt", "TEST3", None),
    File("/005a47bb-5457-4c22-ba27-4d61608f5a37/file3.txt", "TEST4", None),
    File("/273a4a8c-40c8-437d-8e58-9c56dc5f48dd/file3.txt", "TEST5", None))
}
