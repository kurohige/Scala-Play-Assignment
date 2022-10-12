name := """Visma-assignment"""
organization := "com.josehidalgo"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.10"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
    .settings(
      name := """play-scala-hello-world-tutorial""",
      organization := "com.example",
      version := "1.0-SNAPSHOT",
      scalaVersion := "2.13.8",
      libraryDependencies ++= Seq(
        guice,
        "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
        "com.typesafe.play" %% "play-slick" % "5.0.0",
        // play-slick 5.0.x is currently built and tested against version 1.4.200
        "com.h2database" % "h2" % "1.4.200",
        "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
        "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
        "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
      ),
      scalacOptions ++= Seq(
        "-feature",
        "-deprecation:false",
        "-Xfatal-warnings"
      )
    )



