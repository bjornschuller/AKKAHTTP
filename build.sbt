name := "AKKAHTTP"
version := "1.0"
scalaVersion := "2.11.8"

fork in Test := true
(parallelExecution in Test) := false

libraryDependencies ++= {
      val akkaV       = "2.4.8"
      val scalaTestV  = "2.2.5"
      Seq(
        "com.typesafe.akka"   %%  "akka-actor"                           % akkaV,
        "com.typesafe.akka"   %%  "akka-stream"             		         % akkaV,
        "com.typesafe.akka"   %%  "akka-http-experimental"               % akkaV,
        "com.typesafe.akka"   %%  "akka-http-spray-json-experimental"    % akkaV,
        "com.typesafe.akka"   %%  "akka-http-testkit"       		         % akkaV,
        "com.typesafe.akka"   %%  "akka-testkit"                         % akkaV,
        "org.scalatest"       %%  "scalatest"                            % scalaTestV % "test"
      )
    }
