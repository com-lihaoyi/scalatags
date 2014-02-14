import sbt.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

scalaJSSettings

Build.sharedSettings

version := "0.2.3-JS"

unmanagedSourceDirectories in Compile <+= baseDirectory(_ / ".." / "shared" / "main")

unmanagedSourceDirectories in Test <+= baseDirectory(_ / ".." / "shared" / "test")

libraryDependencies ++= Seq(
  "com.lihaoyi.utest" % "utest_2.10" % "0.1.1-JS" % "test",
  "com.lihaoyi.utest" % "utest-runner_2.10" % "0.1.1" % "test"
)


(loadedTestFrameworks in Test) := {
  (loadedTestFrameworks in Test).value.updated(
    sbt.TestFramework(classOf[utest.runner.JsFramework].getName),
    new utest.runner.JsFramework(environment = (scalaJSEnvironment in Test).value)
  )
}
