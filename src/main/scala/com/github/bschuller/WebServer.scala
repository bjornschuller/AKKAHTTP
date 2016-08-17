package com.github.bschuller

import akka.http.scaladsl.Http
import com.github.bschuller.route.AkkaHttpRoute


object WebServer extends App with CoreServices with AkkaHttpRoute{
  Http().bindAndHandle(handler = route, interface = "localhost", port = 8080) map {
    binding => println(s"REST interface bound to ${binding.localAddress}..")
      val banner =
        s"""
           |     _    _  ___  __    _         _   _ _____ _____ ____
           |    / \  | |/ / |/ /   / \       | | | |_   _|_   _|  _ \
           |   / _ \ | ' /| ' /   / _ \ _____| |_| | | |   | | | |_) |
           |  / ___ \| . \| . \  / ___ \_____|  _  | | |   | | |  __/
           | /_/   \_\_|\_\_|\_\/_/   \_\    |_| |_| |_|   |_| |_|
           |LAUNCHING SERVER!
    """.stripMargin
      println(banner)
  }recover
    {
      case ex => println(s"REST interface could not bind", ex.getMessage)
    }
}