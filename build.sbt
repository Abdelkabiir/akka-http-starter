name := "akka-http-server-starter"

version := "0.1"

scalaVersion := "2.13.3"


libraryDependencies ++= {
  val akkaHttpV         = "10.2.0"
  val akkaV             = "2.6.8"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV
  )
}