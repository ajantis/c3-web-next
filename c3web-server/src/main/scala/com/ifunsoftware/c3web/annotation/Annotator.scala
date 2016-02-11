package com.ifunsoftware.c3web.annotation

import com.ifunsoftware.c3web.models.Keyword
import com.ifunsoftware.c3web.service.FilesService
import org.slf4j.LoggerFactory

import scala.collection.MapLike

/**
 * Created by admin on 11.02.2016.
 */
object Annotator {
  def getKeyWords(content: Array[Byte]): List[Keyword] = {
    import scala.collection.mutable.Map
    val fileText = new String(content, "UTF-8");
    val rawKeyWords = fileText.split("\\W+").groupBy(identity).toList.sortWith(_._2.size > _._2.size)
      .map(_._1.toLowerCase.dropRight(1)).filter(_.length >= 5)

    val groupVocabulary = new String(filesService.getVocabulary("").get.content.get, "UTF-8").split('\n').distinct;

    var groupVocabularyMap = Map() ++ (groupVocabulary map (term => term -> 0.0) toMap)

    rawKeyWords.foreach(w =>
      groupVocabularyMap.keys.foreach(k =>
        if (k.toLowerCase.contains(w)) {
          groupVocabularyMap(k) = groupVocabularyMap(k) + 1.0 / (k.split(' ').length)

        }))

    val filteredKeyWords = groupVocabularyMap.toList.sortBy(_._2).reverse.map(_._1).take(15);
    val resultKeyWords = filteredKeyWords.map(k => new Keyword(k.toLowerCase))
    return resultKeyWords;
  }
  private val filesService = FilesService
}
