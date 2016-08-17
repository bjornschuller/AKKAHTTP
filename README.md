
The Akka HTTP modules implement a full server- and client-side HTTP stack on top of akka-actor and akka-stream. It is a general toolkit for providing and consuming HTTP-based services. 
Akka HTTP will merely be used for the HTTP integration needs.

Mind that akka-http comes in two modules: akka-http-experimental and akka-http-core. Because akka-http-experimental depends on akka-http-core you don't need to bring the latter explicitly. Still you may need to this in case you rely solely on low-level API.

#Webserver
You start with creating a webserver. A new HTTP server can be launched using the Http() class. The bindAndHandle() method starts a new HTTP server at the given endpoint and uses the given handler (i.e., the akka http route) Flow for processing all incoming connections.

#CoreServices
Akka-http is based on Akka and like everything dependend on this library it needs ActorSystem to run. There is no difference here, so we need an ActorSystem. 

Moreover, there is another 'Core Service' needed and that is an Materializer. Like Actor needs the ActorSystem - Flows needs a Materializer to operate. But what is Flow? Flow is a part of Akka-Stream, and in short: Flow is a pipe for transporting data;

Thirdly, an ExecutionContext is needed. 


#Routes
The orderRoute unmarshalls an Order from the incoming request saves it in RAM (add Order to List) and replies with an OK when done.

#Route TestKit
One of Akka HTTP's design goals is good testability of the created services. For services built with the Routing DSL Akka HTTP provides a dedicated testkit that makes efficient testing of route logic easy and convenient.
This "route test DSL" is made available with the akka-http-testkit module.







**SOURCES**

http://doc.akka.io/docs/akka/2.4.9-RC1/scala/http/introduction.html

http://doc.akka.io/japi/akka-stream-and-http-experimental/2.0/akka/http/javadsl/Http.html

http://blog.scalac.io/2015/07/30/websockets-server-with-akka-http.html
http://doc.akka.io/docs/akka/2.4.9-RC1/scala/http/routing-dsl/testkit.html#route-testkit