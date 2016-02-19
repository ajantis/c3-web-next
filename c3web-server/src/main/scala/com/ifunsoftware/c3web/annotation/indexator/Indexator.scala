package com.ifunsoftware.c3web.annotation.indexator

import com.ifunsoftware.c3web.annotation.KeyWord

/**
 * Created by admin on 17.02.2016.
 */
abstract class Indexator {
  def GetIndex(fileText: String): List[KeyWord]
}
