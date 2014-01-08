scalaJSSettings

name := "scalatags-js"

version := "0.2.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
)

unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "src" / "main"
