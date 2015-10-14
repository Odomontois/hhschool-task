scalaVersion in Global := "2.11.7"

val testLibs = Seq(
  "org.specs2" %% "specs2-core" % "3.6.4",
  "org.specs2" %% "specs2-scalacheck" % "3.6.4",
  "org.scalacheck" %% "scalacheck" % "1.12.5") map (_ % "test")

libraryDependencies ++= testLibs

scalacOptions in Test ++= Seq("-Yrangepos")

javacOptions in(Compile, doc) ++= Seq(
  "-notimestamp",
  "-linksource",
  "-encoding", "utf-8",
  "-docencoding", "utf-8",
  "-charset", "utf8")