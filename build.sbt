ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "spark-testing-fixtures",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "3.2.1" % "provided",
      "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    ),
  )
