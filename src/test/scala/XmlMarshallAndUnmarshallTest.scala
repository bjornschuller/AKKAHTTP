import com.github.bschuller.{Marshallers, Unmarshallers}
import com.github.bschuller.domain.Order

class XmlMarshallAndUnmarshallTest extends TestSpec with Marshallers with Unmarshallers{

  "1. The xml marshaller and unmarshaller" should {
    "successfully apply the marshalling and unmarshalling of an order " in {

      val order = new Order("Levi Jeans", "Jeans", "$200,00", 4)

      val orderXml = marshalOrder2Xml(order)
      val unmarshalledOrder = nodeSeqToOrder(orderXml)

      order shouldBe unmarshalledOrder

    }

  }
}

