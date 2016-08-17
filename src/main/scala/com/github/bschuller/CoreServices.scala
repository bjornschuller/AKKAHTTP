package com.github.bschuller

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContext

/**
 * Created by bjornschuller on 12/08/16.
 */
trait CoreServices {
  implicit val system: ActorSystem = ActorSystem("ActorSystem")
  implicit val mat: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
}
