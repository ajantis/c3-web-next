package com.ifunsoftware.c3web.routing.files
import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.annotation.Annotator
import com.ifunsoftware.c3web.models.Tag
import com.ifunsoftware.c3web.models.TagEntryJson._
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
          var annotation = Annotator.getKeyWords(file.get.content.get)
          complete(annotation);
        }
      }
  }
  private val filesService = FilesService
}
