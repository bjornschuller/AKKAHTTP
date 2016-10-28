package com.github.bschuller

import com.github.bschuller.domain.Order

import scala.xml.NodeSeq


trait Unmarshallers {
  implicit def nodeSeq2Int(e:NodeSeq): Int = e.text.toInt
  implicit def nodeSeq2String(e:NodeSeq): String = e.text


  def nodeSeqToOrder(xml: NodeSeq): Order ={
    Order(
      name = xml \\ "name",
      productType = xml \\ "productType",
      price = xml \\ "price",
      quantity = xml \\ "quantity"
    )
  }
}
