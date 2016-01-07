name := """user-registration"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  //specs2 % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.9",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M4" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
