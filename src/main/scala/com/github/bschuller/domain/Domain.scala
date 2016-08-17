package com.github.bschuller.domain

case class Order(name: String, productType: String, price: String, quantity: Int)
case class ItemResponse(message: String)