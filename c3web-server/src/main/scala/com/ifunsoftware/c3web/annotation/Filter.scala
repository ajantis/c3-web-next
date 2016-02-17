package com.ifunsoftware.c3web.annotation

import com.ifunsoftware.c3web.annotation.vocabulary.controlledVocabulary.ControlledVocabulary
import com.ifunsoftware.c3web.annotation.vocabulary.Vocabulary
/**
 * Created by admin on 17.02.2016.
 */
class Filter(vocabulary: Vocabulary) {
  def filterKeyWords(terms: List[KeyWord]): List[KeyWord] = {
    if (vocabulary.isInstanceOf[ControlledVocabulary])
      vocabulary.GetIntersection(terms)
    else terms
  }
}
