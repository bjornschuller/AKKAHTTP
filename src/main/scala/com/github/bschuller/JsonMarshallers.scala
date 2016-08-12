package com.github.bschuller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.bschuller.domain.Order
import spray.json.DefaultJsonProtocol

trait JsonMarshallers extends DefaultJsonProtocol with SprayJsonSupport{

  implicit val orderJsonFormat = jsonFormat4(Order)

}