scalaJSSettings

organization := "com.scalatags"

name := "scalatags"

scalaVersion := "2.10.0"

version := "0.3.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
)

unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "src" / "main"

unmanagedSourceDirectories in Test += baseDirectory.value / ".." / "src" / "test"

