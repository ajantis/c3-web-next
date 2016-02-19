package com.ifunsoftware.c3web.annotation.vocabulary.controlledVocabulary

import com.ifunsoftware.c3web.annotation.KeyWord
import com.ifunsoftware.c3web.annotation.vocabulary.Vocabulary
import com.ifunsoftware.c3web.models.Tag
import shapeless.LT.<

/**
 * Created by admin on 17.02.2016.
 */
class ControlledVocabulary(content: String) extends Vocabulary {
  val terms = content.split('\n').distinct;

  def GetAll(): List[KeyWord] = {
    return (terms map (term => new KeyWord(term, 0))).toList
  }

  def GetIntersection(words: List[KeyWord]): List[KeyWord] = {
    import scala.collection.mutable.Map

    var vocabularyMap = Map() ++ (terms map (term => term -> 0.0) toMap)
    words.foreach(w =>
      vocabularyMap.keys.foreach(k =>
        if (k.toLowerCase.contains(w.value)) {
          vocabularyMap(k) = (vocabularyMap(k) + 1.0 / (k.split(' ').length))

        }))

    vocabularyMap.toList.sortBy(_._2).reverse.map(k => new KeyWord(k._1, k._2.toFloat))
  }

  def IsSuitableForText(words: List[String]): Unit = {
    return GetSuitability(words) < 0.25
  }

  def GetSuitability(words: List[String]): Float = {

    var hits = 0;
    terms.foreach(w =>
      words.foreach(k =>
        if (k.toLowerCase.contains(w)) {
          hits += 1;
        }))
    return hits;
  }
}
