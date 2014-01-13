scalaJSSettings

organization := "com.scalatags"

name := "scalatags"

scalaVersion := "2.10.3"

version := "0.3.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
)

unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "shared" / "main"

unmanagedSourceDirectories in Test += baseDirectory.value / ".." / "shared" / "test"

