import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.scalaJSSettings
import twirl.sbt.TwirlPlugin.Twirl

object Build extends sbt.Build{
  lazy val root = project.in(file("."))
                         .settings(crossScalaVersions := Seq("2.10.4", "2.11.0"))
                         .aggregate(js, jvm)

  lazy val js = project.in(file("js"))
                       .settings(sharedSettings ++ scalaJSSettings:_*)
                       .settings(
    version := version.value + "-JS",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "utest" % "0.1.3-JS" % "test",
      "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.3"
    ),
    (loadedTestFrameworks in Test) := {
      (loadedTestFrameworks in Test).value.updated(
        sbt.TestFramework(classOf[utest.runner.JsFramework].getName),
        new utest.runner.JsFramework(environment = (scalaJSEnvironment in Test).value)
      )
    }
  )

  lazy val jvm = project.in(file("jvm"))
                        .settings(sharedSettings ++ Twirl.settings:_*)
                        .settings(
    sourceDirectory in Twirl.twirlCompile <<= (sourceDirectory in Test) / "twirl",
    target in Twirl.twirlCompile <<= (sourceManaged in Test) / "generated-twirl-sources",
    libraryDependencies ++= Seq(
//      "org.fusesource.scalate" %% "scalate-core" % "1.6.1" % "test",
      "com.lihaoyi" %% "utest" % "0.1.3" % "test"
    ),
    testFrameworks += new TestFramework("utest.runner.JvmFramework")
  )

  val sharedSettings = Seq(
    organization := "com.scalatags",
    name := "scalatags",
    scalaVersion := "2.10.4",
    autoCompilerPlugins := true,
    libraryDependencies ++= Seq("com.lihaoyi" %% "acyclic" % "0.1.2" % "provided"),
    addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),

      // Sonatype
    publishArtifact in Test := false,
    version := "0.2.5",
    unmanagedSourceDirectories in Compile <+= baseDirectory(_ / ".." / "shared" / "main"),
    unmanagedSourceDirectories in Test <+= baseDirectory(_ / ".." / "shared" / "test"),
    publishTo <<= version { (v: String) =>
      Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
    },

    pomExtra := (
      <url>https://github.com/lihaoyi/scalatags</url>
        <licenses>
          <license>
            <name>MIT license</name>
            <url>http://www.opensource.org/licenses/mit-license.php</url>
          </license>
        </licenses>
        <scm>
          <url>git://github.com/lihaoyi/scalatags.git</url>
          <connection>scm:git://github.com/lihaoyi/scalatags.git</connection>
        </scm>
        <developers>
          <developer>
            <id>lihaoyi</id>
            <name>Li Haoyi</name>
            <url>https://github.com/lihaoyi</url>
          </developer>
        </developers>
    )
  )
}
