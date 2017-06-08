organization in ThisBuild := "com.andycot"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `referential-webcrawler` = (project in file("."))
  .aggregate(`referential-webcrawler-api`, `referential-webcrawler-impl`, `referential-webcrawler-stream-api`, `referential-webcrawler-stream-impl`)

lazy val `referential-webcrawler-api` = (project in file("referential-webcrawler-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `referential-webcrawler-impl` = (project in file("referential-webcrawler-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`referential-webcrawler-api`)

lazy val `referential-webcrawler-stream-api` = (project in file("referential-webcrawler-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `referential-webcrawler-stream-impl` = (project in file("referential-webcrawler-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`referential-webcrawler-stream-api`, `referential-webcrawler-api`)
