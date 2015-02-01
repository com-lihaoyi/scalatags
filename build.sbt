lazy val scalatags = crossProject
  .settings(
    organization := "com.lihaoyi",
    name := "scalatags",
    scalaVersion := "2.11.4",
    crossScalaVersions := Seq("2.11.4", "2.10.4"),
    autoCompilerPlugins := true,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "acyclic" % "0.1.2" % "provided",
      "com.lihaoyi" %%% "utest" % "0.2.5-RC1" % "test"
    ),
    addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
    libraryDependencies ++= (
      if (scalaVersion.value.startsWith("2.10")) Nil
      else Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.1" % "test")
      ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    // Sonatype
    version := "0.4.3-RC1",
    publishTo := Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"),

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
  .jvmSettings()
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.7.1"
    ),
    resolvers += Resolver.sonatypeRepo("releases"),
    scalaJSStage in Test := FullOptStage,
    requiresDOM := true/*,
      scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
        val a = p.base.toURI.toString.replaceFirst("[^/]+/?$", "")
        val g = "https://raw.githubusercontent.com/lihaoyi/scalatags"
        s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
      }))*/
  )


// Needed, so sbt finds the projects
lazy val scalatagsJVM = scalatags.jvm
lazy val scalatagsJS = scalatags.js


lazy val example = project.in(file("example"))
  .dependsOn(scalatagsJS)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    crossScalaVersions := Seq("2.11.4", "2.10.4"),
    scalaVersion := "2.11.4"
  )