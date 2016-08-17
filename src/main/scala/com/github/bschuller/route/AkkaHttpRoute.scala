package com.github.bschuller.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.{ActorMaterializer, Materializer}
import com.github.bschuller.domain.Order
import com.github.bschuller.{CoreServices, JsonMarshallers}

import scala.concurrent.ExecutionContext

trait AkkaHttpRoute extends CoreServices with JsonMarshallers{

  implicit val system: ActorSystem
  implicit val mat: Materializer
  implicit val ec: ExecutionContext


  val route: Route =
  pathPrefix("order") {
    post {
      entity(as[Order]) {
        order =>
            system.log.info(s"unmarshalled request to $order")
          complete {
            StatusCodes.OK -> order.toString
          }
      }
    }
  }
}
