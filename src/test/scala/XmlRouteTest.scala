import akka.http.scaladsl.model.StatusCodes
import com.github.bschuller.Marshallers
import com.github.bschuller.domain.Order
import com.github.bschuller.route.AkkaHttpRoute

class XmlRouteTest extends TestSpec with Marshallers{
  val httpRoute = new AkkaHttpRoute {}
  val testOrder = new Order(name = "Nike Air Force One", productType = "Shoe", price = "115 EUR", quantity = 1)

  "1. The service" should {
    "return a StatusCode OK for POST requests to the order path where the MediaTypes is set to XML, " +
      "since the endpoint can only marshal XML at this moment..." in {
      Post("/xml/order", marshalOrder2Xml(testOrder)) ~> httpRoute.route ~> check {
        response.status shouldBe StatusCodes.OK
        println(response)
        responseAs[String] shouldEqual "Accepted the order: Nike Air Force One"
      }
    }
  }

}
