import play.sbt.routes.RoutesKeys

name := "gridtp1"
 
version := "1.0" 
      
lazy val `gridtp1` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play27")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
