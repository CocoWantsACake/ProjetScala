import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.1"

val zioVersion: String = "2.0.20"

lazy val root = (project in file("."))
  .settings(
    name := "ProjetScala",

    libraryDependencies ++= Seq (
      "dev.zio" %% "zio"         % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion
    ),

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )