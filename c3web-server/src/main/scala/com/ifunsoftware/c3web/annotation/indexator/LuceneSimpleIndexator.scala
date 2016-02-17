package com.ifunsoftware.c3web.annotation.indexator

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{ Field, FieldType, Document }
import org.apache.lucene.index._
import org.apache.lucene.store.RAMDirectory

/**
 * Created by admin on 17.02.2016.
 */
class LuceneSimpleIndexator extends Indexator {
  import scala.collection.mutable.Map
  def GetIndex(fileText: String): List[String] = {
    var result = Map[String, Int]()
    var analyzer = new StandardAnalyzer();
    var index = new RAMDirectory();
    var config = new IndexWriterConfig(analyzer);
    var writer = new IndexWriter(index, config);

    var doc = new Document()
    var customFieldType = new FieldType();
    customFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS)
    customFieldType.setStored(true);
    customFieldType.setStoreTermVectors(true);
    doc.add(new Field("content", fileText, customFieldType));

    writer.addDocument(doc)

    val reader = DirectoryReader.open(writer, false)
    var fields = MultiFields.getFields(reader);
    var liveDocs = MultiFields.getLiveDocs(reader);

    if (fields != null) {
      var terms = fields.terms("content");
      if (terms != null) {
        var termsEnum = terms.iterator();
        var term = termsEnum.next

        while (term != null) {
          var docsEnum = termsEnum.docs(liveDocs, null);

          result += term.utf8ToString -> 0;
          val frequency = docsEnum.freq
          term = termsEnum.next
        }
        return result.toList.sortBy(_._2).reverse.map(_._1).take(100);
      }
    }
    return null

  }
}
