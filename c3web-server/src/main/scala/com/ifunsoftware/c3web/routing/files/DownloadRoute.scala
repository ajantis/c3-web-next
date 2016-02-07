package com.ifunsoftware.c3web.routing.files
import akka.actor.{ Actor, Props }
import com.ifunsoftware.c3web.models._
import com.ifunsoftware.c3web.models.FileEntryJson._
import com.ifunsoftware.c3web.models.MetadataEntryJson._
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

object DownloadRoute {
  def props: Props = Props(new DownloadRoute())
}

class DownloadRoute() extends Actor with DownloadRouteTrait {
  def actorRefFactory = context
  def receive = runRoute(downloadRoute)
}

trait DownloadRouteTrait extends HttpService with SprayJsonSupport {

  val log = LoggerFactory.getLogger(classOf[DownloadRouteTrait])

  val downloadRoute = get {
    pathEnd {
      complete(StatusCodes.NoContent)
    } ~
      path(Segment) { filePath =>
        log.debug(s"Hitting Get Download File by Url:${filePath}")
        val file = (filesService.getFileByUrl(filePath))
        if (!file.isDefined && !file.get.content.isDefined)
          complete(StatusCodes.NoContent)
        else {

          val contentType = MediaTypes.getForKey(file.get.contentType.split('/')(0), file.get.contentType.split('/')(1)) match {
            case None     => MediaTypes.`text/plain`
            case Some(ct) => ct
          }

          respondWithMediaType(contentType) {
            respondWithHeader(HttpHeaders.`Content-Disposition`.apply("attachment", Map("filename" -> (file.get.url.split('/').last)))) {
              implicit val bufferMarshaller = {
                BasicMarshallers.byteArrayMarshaller(contentType)
              }
              autoChunk(ChunkSizeMagnet.fromChunkSize(5000000)) {
                {
                  complete(file.get.content.get)
                }
              }
            }
          }
        }

      }
  }
  private val filesService = FilesService
}
