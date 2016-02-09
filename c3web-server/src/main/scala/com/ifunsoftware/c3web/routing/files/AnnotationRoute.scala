package com.ifunsoftware.c3web.routing.files
import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models.Keyword
import com.ifunsoftware.c3web.models.KeywordEntryJson._
import com.ifunsoftware.c3web.service.{ FilesService }
import org.slf4j.LoggerFactory
import spray.http.{ MediaTypes, HttpHeaders, StatusCodes }
import spray.httpx.SprayJsonSupport
import spray.httpx.marshalling.BasicMarshallers
import spray.routing.HttpService
import spray.routing.directives.ChunkSizeMagnet

/**
 * Created by admin on 07.02.2016.
 */

object AnnotationRoute {
  def props: Props = Props(new AnnotationRoute())
}

class AnnotationRoute() extends Actor with AnnotationRouteTrait {
  def actorRefFactory = context
  def receive = runRoute(annotationRoute)
}

trait AnnotationRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[AnnotationRouteTrait])

  val annotationRoute = get {
    pathEnd {
      complete(StatusCodes.NoContent)
    } ~
      path(Segment) { filePath =>
        log.debug(s"Hitting Get Annotation by Url:${filePath}")
        val file = (filesService.getFileByUrl(filePath))
        if (!file.isDefined && !file.get.content.isDefined)
          complete(StatusCodes.NoContent)
        else {
          val fileText = new String(file.get.content.get, "UTF-8");
          val annotation = fileText.split("\\W+").groupBy(identity).toList.sortWith(_._2.size > _._2.size)
            .map(_._1).filter(_.name.length > 5).take(5).map(k => new Keyword(k))
          complete(annotation);
        }
      }
  }
  private val filesService = FilesService
}
