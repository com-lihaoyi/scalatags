import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.scalaJSSettings
import twirl.sbt.TwirlPlugin.Twirl

object Build extends sbt.Build{
  val cross = new utest.jsrunner.JsCrossBuild(
    organization := "com.scalatags",
    name := "scalatags",
    scalaVersion := "2.10.4",
    autoCompilerPlugins := true,
    libraryDependencies ++= Seq("com.lihaoyi" %% "acyclic" % "0.1.2" % "provided"),
    addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),

    libraryDependencies ++= (
      if (scalaVersion.value.startsWith("2.10")) Nil
      else Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.1")
    ),

    // Sonatype
    version := "0.2.5",
    publishTo <<= version { (v: String) =>
      Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
    },

    pomExtra :=
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
  lazy val root = cross.root

  lazy val js = cross.js.settings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.4"
    )
  )

  lazy val jvm = cross.jvm

}
