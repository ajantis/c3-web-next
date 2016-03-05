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
        if (k.toLowerCase.equals(w.name)) {
          vocabularyMap(k) = (vocabularyMap(k) + 2.0)

        } else if (k.toLowerCase.contains(w.name)) {
          vocabularyMap(k) = (vocabularyMap(k) + 1.0 / (k.split(' ').length))

        }))

    vocabularyMap.toList.distinct.sortBy(_._2).reverse.map(k => new KeyWord(k._1, k._2.toFloat))
  }

  def IsSuitableForText(words: List[String]): Boolean = {
    return GetSuitability(words) / terms.length > 0.25
  }

  def GetSuitability(words: List[String]): Float = {
    var hits = 0;
    terms.foreach(k =>
      words.foreach(w =>
        k.split(' ').foreach(k_part =>
          if (k_part.toLowerCase.equals(w)) {
            hits += 1;
          })))
    return hits;
  }
}
