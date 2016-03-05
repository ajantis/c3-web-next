package com.ifunsoftware.c3web.annotation.vocabulary

import com.ifunsoftware.c3web.annotation.KeyWord

/**
 * Created by admin on 17.02.2016.
 */
abstract class Vocabulary {
  def GetAll(): List[KeyWord]
  def GetIntersection(words: List[KeyWord]): List[KeyWord]
}
