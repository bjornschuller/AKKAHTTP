package com.github.bschuller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport
import com.github.bschuller.domain.{ItemResponse, Order}
import spray.json.DefaultJsonProtocol

import scala.xml.NodeSeq

trait Marshallers extends DefaultJsonProtocol with SprayJsonSupport with ScalaXmlSupport{

  implicit val orderJsonFormat = jsonFormat4(Order)
  implicit val itemResponseJsonFormat = jsonFormat1(ItemResponse)

  // TODO fix it for xml
//  def marshalOrder2Xml(order: Order): NodeSeq =
//    <order>
//      <name>
//        { order.name }
//      </name>
//      <productType>
//        { order.productType }
//      </productType>
//      <price>
//        { order.price }
//      </price>
//      <quantity>
//        { order.quantity }
//      </quantity>
//    </order>

}


