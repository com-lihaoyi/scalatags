lazy val root = project.in(file(".")).dependsOn(file("../../microtest/js-plugin"))

addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.3-SNAPSHOT")

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += Classpaths.sbtPluginSnapshots

//addSbtPlugin("io.spray" % "sbt-twirl" % "0.7.0-SNAPSHOT")

addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.1.0")

