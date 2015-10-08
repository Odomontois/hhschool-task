scalaVersion in Global := "2.11.7"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.6.4",
  "org.specs2" %% "specs2-scalacheck" % "3.6.4",
  "org.scalacheck" %% "scalacheck" % "1.12.5") map (_ % "test")

scalacOptions in Test ++= Seq("-Yrangepos")