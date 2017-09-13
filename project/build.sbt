
// 0.6.20 breaks Java7 - https://github.com/scala-js/scala-js/issues/3128
val scalaJSVersion = Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.19")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.3.4")
addSbtPlugin("com.lihaoyi" % "scalatex-sbt-plugin" % "0.3.9")
addSbtPlugin("org.scala-native"  % "sbt-crossproject"         % "0.2.2")
addSbtPlugin("org.scala-native"  % "sbt-scala-native"         % "0.3.3")

// For sbt-crossproject support even with Scala.js 0.6.x
{
  if (scalaJSVersion.startsWith("0.6."))
    Seq(addSbtPlugin("org.scala-native" % "sbt-scalajs-crossproject" % "0.2.2"))
  else
    Nil
}

// PhantomJSEnv
{
  if (scalaJSVersion.startsWith("0.6.")) Nil
  else Seq(addSbtPlugin("org.scala-js" % "sbt-scalajs-env-phantomjs" % "1.0.0-M1"))
}
libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "8.1.16.v20140903"
libraryDependencies += "org.eclipse.jetty" % "jetty-websocket" % "8.1.16.v20140903"