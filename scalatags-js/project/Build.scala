import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
object Build extends sbt.Build {
  lazy val root = project.in(file("."))
    .settings(scalaJSSettings: _*)
    .settings(
      name := "scalatags-js",
      version := "0.1.4",
      unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "src" / "main" // test sources are omitted from js build
    )
}
