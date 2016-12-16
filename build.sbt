sbtPlugin := true

name := "sbt-laika-blog"

organization := "com.vshalts"

version := "0.1.0"

scalaVersion := "2.10.6"

scalaVersion in Global := "2.10.6"

scalacOptions ++= Seq("-deprecation", "-unchecked")

resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("org.planet42"       % "laika-sbt"           % "0.6.0")

addSbtPlugin("com.typesafe.sbt"   % "sbt-site"            % "1.2.0-RC1")

scriptedSettings

scriptedLaunchOpts += "-Dproject.version="+version.value

scriptedBufferLog := true
