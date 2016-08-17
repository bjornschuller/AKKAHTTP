import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.{ActorMaterializer, Materializer}
import com.github.bschuller.JsonMarshallers
import com.github.bschuller.domain.Order
import com.github.bschuller.route.AkkaHttpRoute
import org.scalatest.{Matchers, WordSpec}


class OrderRouteTest extends WordSpec with Matchers with ScalatestRouteTest with JsonMarshallers {


  val httpRoute = new AkkaHttpRoute {}
  val testOrder = new Order(name = "Nike Air Force One", productType = "Shoe", price = "115 EUR", quantity = 1)

  "The service" should {
    "return a StatusCode OK for POST requests to the order path" in {
      Post("/order", testOrder) ~> httpRoute.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldEqual testOrder.toString
      }
    }
  }
}
