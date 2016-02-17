package com.ifunsoftware.c3web.annotation.indexator

/**
 * Created by admin on 17.02.2016.
 */
abstract class Indexator {
  def GetIndex(fileText: String): List[String]
}
