package com.github.bschuller.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.github.bschuller.domain.{ItemResponse, Order}
import com.github.bschuller.{CoreServices, JsonMarshallers}

import scala.concurrent.ExecutionContext

trait AkkaHttpRoute extends CoreServices with JsonMarshallers{

  implicit val system: ActorSystem
  implicit val mat: Materializer
  implicit val ec: ExecutionContext


  // TODO implement some functionality inside the route
  val route: Route =
    path("order") {
      post {
        entity(as[Order]) {
          order =>
              system.log.info(s"unmarshalled request to $order")
            complete {
              StatusCodes.OK -> order.toString
            }
        }
      }
    } ~
    get{
      pathPrefix("item" / LongNumber){ id =>
        system.log.info(s"doing a get for $id")
          complete{
            StatusCodes.OK -> ItemResponse(s"The item with id: $id is in stock").toString
          }
      }
    }
}
