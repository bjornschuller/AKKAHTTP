package com.github.bschuller.route

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.stream.Materializer
import com.github.bschuller.domain.Order
import com.github.bschuller.{CoreServices, Marshallers, Unmarshallers}

import scala.concurrent.ExecutionContext
import spray.json._

import scala.xml.NodeSeq // mandatory to use toJson

trait AkkaHttpRoute extends CoreServices with Marshallers with Unmarshallers{

  implicit val system: ActorSystem
  implicit val mat: Materializer
  implicit val ec: ExecutionContext

  val routeExceptionHandler: ExceptionHandler = {
    ExceptionHandler {
      case ex: java.lang.NumberFormatException =>
        extractUri { uri =>
          system.log.info(s"Request to $uri could not be handled normally because an exception occurred ${ex}")
          complete(StatusCodes.BadRequest -> "Number formate exception occurred")
      }
    }
  }

  val route: Route =
    path("json"/"order") {
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
    path("xml"/"order") {
      handleExceptions(routeExceptionHandler) {
        post {
          entity(as[NodeSeq]) {
            orderXml =>
              complete {
                system.log.info(s"The incoming request is fully consumed")
                val order = nodeSeqToOrder(orderXml)
                StatusCodes.OK -> s"Accepted the order: ${order.name}"
              }
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
