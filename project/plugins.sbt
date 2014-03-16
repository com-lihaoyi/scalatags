addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.4.0")

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

//resolvers += Classpaths.sbtPluginSnapshots

//addSbtPlugin("io.spray" % "sbt-twirl" % "0.7.0-SNAPSHOT")

addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.1.2")

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "acyclic" % "0.1.1" % "provided"
)

autoCompilerPlugins := true

addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.1")
