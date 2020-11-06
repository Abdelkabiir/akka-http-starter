import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.model.HttpMethods.GET
import akka.stream.scaladsl.Sink
import scala.concurrent.{ExecutionContextExecutor, Future}

object Server extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  // new server instance
  val serverSource = Http()
    .newServerAt("localhost", 8080)
    .connectionSource()

  // requests handler
  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(entity = HttpEntity("You requested home route"))
    case HttpRequest(GET, Uri.Path("/about"), _, _, _) =>
      HttpResponse(entity = HttpEntity("You requested about route"))
    case _ => HttpResponse(404, entity = "You requested a wrong route")
  }

  // handle requests from connections
  val bindingFuture: Future[Http.ServerBinding] =
    serverSource.to(Sink.foreach { connection  =>
      println("Accepted new connection from " + connection.remoteAddress)
      connection handleWithSyncHandler(requestHandler)
    }).run()

  println("server online at : http://localhost:8080 ")
  scala.io.StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
