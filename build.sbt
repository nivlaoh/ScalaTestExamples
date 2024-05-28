ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.19"
val scalatestVersion = "3.2.18"

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    "org.scalatestplus" %% "scalacheck-1-17" % "3.2.18.0" % Test
  )
)

val seleniumSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatestplus" %% "selenium-4-17" % "3.2.18.0" % Test,
    "org.seleniumhq.selenium" % "selenium-chrome-driver" % "4.21.0" % Test
  )
)

lazy val basicExamples = (project in file("basic-examples"))
  .settings(
    name := "basic-examples"
  )
  .settings(commonSettings)

lazy val advanceExamples = (project in file("advance-examples"))
  .settings(
    name := "advance-examples"
  )
  .settings(commonSettings, seleniumSettings)

lazy val root = (project in file("."))
  .settings(
    name := "ScalaTestExamples",
    description := "Collection of ScalaTest examples",
    developers := List(Developer("ah", "Alvin Ho", "alvin.ho@b---.com", new URL("https://")))
  )
  .settings(commonSettings)
  .aggregate(basicExamples, advanceExamples)
