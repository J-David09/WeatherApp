name := """WeatherApp"""
organization := "com.softcaribbean"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.28.0"
libraryDependencies += "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M1"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.13"
libraryDependencies += "com.github.cb372" %% "scalacache-caffeine" % "0.28.0"

