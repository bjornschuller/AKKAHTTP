package com.github.bschuller.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.github.bschuller.domain.Order
import com.github.bschuller.{CoreServices, Marshallers}

import scala.concurrent.ExecutionContext
import spray.json._ // mandatory to use toJson

trait AkkaHttpRoute extends CoreServices with Marshallers{

  implicit val system: ActorSystem
  implicit val mat: Materializer
  implicit val ec: ExecutionContext

  // TODO implement some functionality inside the route
  val route: Route =
    path("order") {
      post {
        entity(as[Order]) {
          order =>
          system.log.info(s"The incoming request is fully consumed and unmarshalled to $order, done by entity(as[Order])")
            // you can add some additional logic here and complete with a response
            complete {
              StatusCodes.OK -> s"Accepted the order: ${order.name}".toJson
            }
        }
      }
    } ~
    get{
      pathPrefix("item" / LongNumber){ id =>
        system.log.info(s"doing a get for $id")
          complete{
            StatusCodes.OK -> s"The item with id: $id is in stock".toJson
          }
      }
    }
}
