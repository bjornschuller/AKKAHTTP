import akka.http.scaladsl.model.{ContentTypes, MediaRange, MediaTypes, StatusCodes}
import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.server.UnacceptedResponseContentTypeRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.bschuller.Marshallers
import com.github.bschuller.domain.{ItemResponse, Order}
import com.github.bschuller.route.AkkaHttpRoute
import org.scalatest.{Matchers, WordSpec}
import spray.json._
/*
See: https://github.com/spray/spray-json how to convert json to Object and the other way around
 */
class RouteTest extends WordSpec with Matchers with ScalatestRouteTest with Marshallers {


  val httpRoute = new AkkaHttpRoute {}
  val testOrder = new Order(name = "Nike Air Force One", productType = "Shoe", price = "115 EUR", quantity = 1)

  val AcceptJson = Accept(MediaRange(MediaTypes.`application/json`))
  val AcceptXml = Accept(MediaRange(MediaTypes.`text/xml`))

  "The service" should {
    "return a StatusCode OK for POST requests to the order path where the MediaTypes is set to JSON, " +
      "since the endpoint can only marshal Json at this moment..." in {
      Post("/order", testOrder).withHeaders(AcceptJson) ~> httpRoute.route ~> check {
        status shouldBe StatusCodes.OK

        val response = responseAs[String]
        system.log.info(s"response =  $response")
        response.parseJson.convertTo[ItemResponse] shouldEqual ItemResponse(s"Accepted the order: ${testOrder.name}")
      }
    }
  }

  "The service" should {
    "*reject* requests for application/xml since the endpoint can only marshal Json at this moment and no XML..." in {
      Post("/order", testOrder).withHeaders(AcceptXml) ~> httpRoute.route ~> check {
        handled should ===(false)
        rejection should ===(UnacceptedResponseContentTypeRejection(Set(ContentTypes.`application/json`)))

      }
    }
  }

  "The service" should {
    "return a StatusCode OK for GET requests to the item path" in {
      Get("/item/1") ~> httpRoute.route ~> check {
        val response = responseAs[String]
        system.log.info(s"response =  $response")
        status shouldBe StatusCodes.OK
      }
    }
  }
}