package com.github.bschuller.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.bschuller.{JsonMarshallers, CoreServices}
import com.github.bschuller.domain.Order


trait AkkaHttpRoute extends CoreServices with JsonMarshallers{

  val route: Route =
  pathPrefix("hello") {
    post {
      entity(as[Order]) {
        order =>
          system.log.info(s"received order request")
          complete {
            ??? // do something
          }
      }
    }
  }
}
