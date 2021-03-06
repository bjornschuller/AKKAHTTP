import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.server.UnacceptedResponseContentTypeRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.bschuller.Marshallers
import com.github.bschuller.domain.Order
import com.github.bschuller.route.AkkaHttpRoute
import org.scalatest.{Matchers, WordSpec}
import spray.json._
/*
See: https://github.com/spray/spray-json how to convert json to Object and the other way around
 */
class JsonRouteTest extends TestSpec with Marshallers {


  val httpRoute = new AkkaHttpRoute {}
  val testOrder = new Order(name = "Nike Air Force One", productType = "Shoe", price = "115 EUR", quantity = 1)

  val AcceptJson = Accept(MediaRange(MediaTypes.`application/json`))
  val AcceptXml = Accept(MediaRange(MediaTypes.`text/xml`))

  "1. The service" should {
    "return a StatusCode OK for POST requests to the order path where the MediaTypes is set to JSON, " +
      "since the endpoint can only marshal Json at this moment..." in {
      Post("/json/order", testOrder).withHeaders(AcceptJson) ~> httpRoute.route ~> check {
        response.status shouldBe StatusCodes.OK
        response.entity.contentType shouldBe ContentTypes.`application/json`
        responseAs[String] shouldEqual """"Accepted the order: Nike Air Force One""""
      }
    }
  }

    "2. The service" should {
      "*reject* requests for application/xml since the endpoint can only marshal Json and no XML..." in {
        Post("/json/order", testOrder).withHeaders(AcceptXml) ~> httpRoute.route ~> check {
          handled should ===(false)
          rejection should ===(UnacceptedResponseContentTypeRejection(Set(ContentTypes.`application/json`)))

        }
      }
    }

    "3. The service" should {
      "return a StatusCode OK for GET requests to the item path" in {
        Get("/item/1") ~> httpRoute.route ~> check {
          val response = responseAs[String]
          system.log.info(s"response =  $response")
          status shouldBe StatusCodes.OK
        }
      }
    }

}