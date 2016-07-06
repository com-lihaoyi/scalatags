scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.10.4")

lazy val scalatags = crossProject
  .settings(
    organization := "com.lihaoyi",
    name := "scalatags",
    scalaVersion := "2.11.8",

    autoCompilerPlugins := true,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "acyclic" % "0.1.2" % "provided",
      "com.lihaoyi" %%% "utest" % "0.3.1" % "test",
      "com.lihaoyi" %%% "sourcecode" % "0.1.0",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"
    ) ++ (
      if (scalaVersion.value startsWith "2.11.") Nil
      else Seq(
        "org.scalamacros" %% s"quasiquotes" % "2.0.0" % "provided",
        compilerPlugin("org.scalamacros" % s"paradise" % "2.0.0" cross CrossVersion.full)
      )
    ),
    addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
    libraryDependencies ++= (
      if (scalaVersion.value.startsWith("2.10")) Nil
      else Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.1" % "test")
      ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    // Sonatype
    version := _root_.scalatags.Constants.version,
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
    scalaJSUseRhino in Global := false,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1"
    ),
    resolvers += Resolver.sonatypeRepo("releases"),
    scalaJSStage in Test := FullOptStage,
    requiresDOM := true,
    scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
      val a = baseDirectory.value.toURI.toString.replaceFirst("[^/]+/?$", "")
      val g = "https://raw.githubusercontent.com/lihaoyi/scalatags"
      s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
    }))
  )


// Needed, so sbt finds the projects
lazy val scalatagsJVM = scalatags.jvm
lazy val scalatagsJS = scalatags.js


lazy val example = project.in(file("example"))
  .dependsOn(scalatagsJS)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    crossScalaVersions := Seq("2.11.4", "2.10.4"),
    scalaVersion := "2.11.4",
    scalacOptions ++= Seq(
      "-deprecation", // warning and location for usages of deprecated APIs
      "-feature", // warning and location for usages of features that should be imported explicitly
      "-unchecked", // additional warnings where generated code depends on assumptions
      "-Xlint", // recommended additional warnings
      "-Xcheckinit", // runtime error when a val is not initialized due to trait hierarchies (instead of NPE somewhere else)
      "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
      "-Ywarn-inaccessible",
      "-Ywarn-dead-code"
    )
  )

lazy val readme = scalatex.ScalatexReadme(
  projectId = "readme",
  wd = file(""),
  url = "https://github.com/lihaoyi/scalatags/tree/master",
  source = "Readme",
  autoResources = Seq("Autocomplete.png", "ErrorHighlighting.png", "InlineDocs.png", "example-opt.js")
).settings(
  scalaVersion := "2.11.8",
  (unmanagedSources in Compile) += baseDirectory.value/".."/"project"/"Constants.scala",
  (resources in Compile) += (fullOptJS in (example, Compile)).value.data,
  (resources in Compile) += (doc in (scalatagsJS, Compile)).value,
  (run in Compile) <<= (run in Compile).dependsOn(Def.task{
    sbt.IO.copyDirectory(
      (doc in (scalatagsJS, Compile)).value,
      (target in Compile).value/"scalatex"/"api",
      overwrite = true
    )
  })
)
