package com.ifunsoftware.c3web.annotation

import com.ifunsoftware.c3web.annotation.indexator.LuceneSimpleIndexator
import com.ifunsoftware.c3web.annotation.vocabulary.controlledVocabulary.ControlledVocabulary
import com.ifunsoftware.c3web.models.{ File, Tag }
import com.ifunsoftware.c3web.service.FilesService
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{ FieldType, Field, TextField, Document }
import org.apache.lucene.index._
import org.apache.lucene.search.{ DocIdSetIterator, IndexSearcher }
import org.apache.lucene.store.{ RAMDirectory, Directory }
import org.apache.lucene.util.BytesRef
import org.slf4j.LoggerFactory
import scala.collection.MapLike

/**
 * Created by admin on 11.02.2016.
 */
object Annotator {
  def getKeyWords(content: Array[Byte]): List[Tag] = {
    val fileText = new String(content, "UTF-8");

    val rawKeyWords = (new LuceneSimpleIndexator()).GetIndex(fileText)

    val baseControlledVocabulary = new ControlledVocabulary(new String(filesService.getVocabulary("").get.content.get, "UTF-8"))

    val resultKeyWords = (new Annotation(rawKeyWords))
      .applyFilter(new Filter(baseControlledVocabulary)).getTags();
    return resultKeyWords.take(20);
  }

  private val filesService = FilesService
}
