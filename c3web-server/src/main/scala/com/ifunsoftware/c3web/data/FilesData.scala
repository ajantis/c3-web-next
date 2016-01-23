package com.ifunsoftware.c3web.data

import com.ifunsoftware.c3web.models.{ File, Group }

import scala.collection.mutable.ArrayBuffer

/**
 * Created by admin on 23.01.2016.
 */
object FileData {
  val fileMock = ArrayBuffer(
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file1.txt", "TEST1", null),
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file2.txt", "TEST2", null),
    File("/3cb27531-6ba1-4270-b7bc-246d89b8502f/file3.txt", "TEST3", null))
}
