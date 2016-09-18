package com.github.bschuller

import akka.http.scaladsl.Http
import com.github.bschuller.route.AkkaHttpRoute

import scala.util.{Failure, Success}


object WebServer extends CoreServices with AkkaHttpRoute{

  def startServer = {
    val binding = Http().bindAndHandle(handler = route, interface = "localhost", port = 8080)

    binding.onComplete{
      case Success(x) => {
        println(s"REST interface bound to ${x.localAddress}..")
        val banner = """
                 |     _    _  ___  __    _         _   _ _____ _____ ____
                 |    / \  | |/ / |/ /   / \       | | | |_   _|_   _|  _ \
                 |   / _ \ | ' /| ' /   / _ \ _____| |_| | | |   | | | |_) |
                 |  / ___ \| . \| . \  / ___ \_____|  _  | | |   | | |  __/
                 | /_/   \_\_|\_\_|\_\/_/   \_\    |_| |_| |_|   |_| |_|
                 |LAUNCHING SERVER!""".stripMargin
            println(banner)
        }
      case Failure(e) => println(s"Binding failed with ${e.getMessage}")
    }
  }
}