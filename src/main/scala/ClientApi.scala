import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object ClientApi extends App {
  implicit val system = ActorSystem()
  implicit val ec = ExecutionContext.global

  val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:8080/about"))

  responseFuture
    .onComplete {
      case Success(res) => res.entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        println(body.utf8String)}
      case Failure(_)   => sys.error("something wrong")
    }(ec)

}
