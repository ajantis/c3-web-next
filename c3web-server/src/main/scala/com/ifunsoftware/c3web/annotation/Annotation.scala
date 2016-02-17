package com.ifunsoftware.c3web.annotation
import com.ifunsoftware.c3web.models.{ File, Tag }
/**
 * Created by admin on 17.02.2016.
 */
class Annotation(keyWords: List[KeyWord]) {

  def applyFilter(filter: Filter): Annotation =
    {
      return new Annotation(filter.filterKeyWords(keyWords))
    }

  def getTags(): List[Tag] = keyWords.map(k => new Tag(k.value))
}
