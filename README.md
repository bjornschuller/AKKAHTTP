
The Akka HTTP modules implement a full server- and client-side HTTP stack on top of akka-actor and akka-stream. It is a general toolkit for providing and consuming HTTP-based services. 
Akka HTTP will merely be used for the HTTP integration needs.

Mind that akka-http comes in two modules: akka-http-experimental and akka-http-core. Because akka-http-experimental depends on akka-http-core you don't need to bring the latter explicitly. Still you may need to this in case you rely solely on low-level API.

#Webserver
You start with creating a webserver. A new HTTP server can be launched using the Http() class.
The bindAndHandle() method starts a new HTTP server at the given endpoint and uses the given handler (i.e., the akka http route) Flow
for processing all incoming connections.  So the Route created using the Route DSL is  "bound" to a port to start serving HTTP requests, example:
   Http().bindAndHandle(handler = route, interface = "localhost", port = 8080)

#CoreServices
Akka-http is based on Akka and like everything dependend on this library it needs ActorSystem to run. There is no difference here, so we need an ActorSystem. 

Moreover, there is another 'Core Service' needed and that is an Materializer. Like Actor needs the ActorSystem - Flows needs a Materializer to operate. But what is Flow? Flow is a part of Akka-Stream, and in short: Flow is a pipe for transporting data;

Thirdly, an ExecutionContext is needed. 

#Routing DSL for HTTP servers
The high-level, routing API of Akka HTTP provides a DSL to describe HTTP "routes" and how they should be handled. You can complete a
request with any kind of object a as long as there is an implicit marshaller available in scope.

One of the strengths of Akka HTTP is that streaming data is at its heart meaning that both request and response bodies can be
streamed through the server achieving constant memory usage even for very large requests or responses.
Streaming responses will be backpressured by the remote client so that the server will not push data faster than
the client can handle, streaming requests means that the server decides how fast the remote client can push the data of the request body.

Next to that, Akka HTTP routes easily interacts with actors. Meaning that you can also use the actor functionality inside your route.

#HTTP client API
In contrast, to creating a webserver and routes you can also create client API's. The client APIs provide methods
for calling a HTTP server using the same HttpRequest and HttpResponse abstractions that Akka HTTP server uses
but adds the concept of connection pools to allow multiple requests to the same server to be handled more
performantly by re-using TCP connections to the server.

#Route TestKit
One of Akka HTTP's design goals is good testability of the created services. For services built with the Routing DSL
Akka HTTP provides a dedicated testkit that makes efficient testing of route logic easy and convenient.
This "route test DSL" is made available with the akka-http-testkit module.

#2. Common Abstractions (Client- and Server-Side)

**2.1 HTTP Model**

**HttpRequest** and **HttpResponse** are the basic case classes representing HTTP messages.

**An HttpRequest consists of:**

1. a method (GET, POST, etc.)
2. a URI
3. a seq of headers
4. an entity (body data)
5. a protocol

**An HttpResponse consists of**
1. a status code
2. a seq of headers
3. an entity (body data)
4. a protocol


#3. Marshalling
"Marshalling" is the process of converting a higher-level (object) structure into some kind of lower-level representation, often a "wire format" (json, xml or binary encodings).
Other popular names for it are "Serialization" or "Pickling".

In Akka HTTP "Marshalling" means the conversion of an object of type T into a lower-level target type, e.g. a MessageEntity
(which forms the "entity body" of an HTTP request or response) or a full HttpRequest or HttpResponse.

Marshalling of instances of type A into instances of type B is performed by a Marshaller[A, B].
Akka HTTP also predefines a number of helpful aliases for the types of marshallers that you'll likely work with most:

1. type ToEntityMarshaller[T] = Marshaller[T, MessageEntity]
2. type ToHeadersAndEntityMarshaller[T] = Marshaller[T, (immutable.Seq[HttpHeader], MessageEntity)]
3. type ToResponseMarshaller[T] = Marshaller[T, HttpResponse]
4. type ToRequestMarshaller[T] = Marshaller[T, HttpRequest]

for more information see: http://doc.akka.io/docs/akka/2.4.8/scala/http/common/marshalling.html

#4. Unmarshalling
"Unmarshalling" is the process of converting some kind of a lower-level representation, often a "wire format" (json, xml or binary encodings),
into a higher-level (object) structure. Other popular names for it are "Deserialization" or "Unpickling". In Akka HTTP "Unmarshalling"
means the conversion of a lower-level source object, e.g. a MessageEntity (which forms the "entity body" of an HTTP request or response)
or a full HttpRequest or HttpResponse, into an instance of type T. Simple explained, it is the conversion of xml, json or some
binary encoding to a custom type like Order (i.e., case class).

The unmarshalling infrastructure of Akka HTTP relies on a type-class based approach, which means that Unmarshaller
instances from a certain type A to a certain type B have to be available implicitly.

Akka HTTP also predefines a number of helpful aliases for the types of unmarshallers that you'll likely work with most:
1. type FromEntityUnmarshaller[T] = Unmarshaller[HttpEntity, T]
2. type FromMessageUnmarshaller[T] = Unmarshaller[HttpMessage, T]
3. type FromResponseUnmarshaller[T] = Unmarshaller[HttpResponse, T]
4. type FromRequestUnmarshaller[T] = Unmarshaller[HttpRequest, T]
5. type FromStringUnmarshaller[T] = Unmarshaller[String, T]
6. type FromStrictFormFieldUnmarshaller[T] = Unmarshaller[StrictForm.Field, T]



**SOURCES**

http://doc.akka.io/docs/akka/2.4.8/scala/http/
http://doc.akka.io/japi/akka-stream-and-http-experimental/2.0/akka/http/javadsl/Http.html

http://blog.scalac.io/2015/07/30/websockets-server-with-akka-http.html
http://doc.akka.io/docs/akka/2.4.9-RC1/scala/http/routing-dsl/testkit.html#route-testkit