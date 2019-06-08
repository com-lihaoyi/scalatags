addSbtPlugin("com.lihaoyi" % "scalatex-sbt-plugin" % "0.3.11")

val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.28")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.2.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.9.4")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.6.0")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.8")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "0.6.0")

