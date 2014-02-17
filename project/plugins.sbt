addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.3")

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

//resolvers += Classpaths.sbtPluginSnapshots

//addSbtPlugin("io.spray" % "sbt-twirl" % "0.7.0-SNAPSHOT")

addSbtPlugin("com.lihaoyi.utest" % "utest-js-plugin" % "0.1.1")

libraryDependencies ++= Seq(
  "com.lihaoyi.utest" % "utest-runner_2.10" % "0.1.1",
  "com.lihaoyi.acyclic" %% "acyclic" % "0.1.0" % "provided"
)

autoCompilerPlugins := true

addCompilerPlugin("com.lihaoyi.acyclic" %% "acyclic" % "0.1.0")