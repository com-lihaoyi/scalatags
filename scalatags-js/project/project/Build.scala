import sbt._
import Keys._

object Build extends sbt.Build {
  import sbt._

  override lazy val projects = Seq(root)
  lazy val root =
    Project("plugins", file("."))
      .settings(
      resolvers += Resolver.url("scala-js-releases", url("http://repo.scala-js.org/repo/releases/"))(Resolver.ivyStylePatterns),
        addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.1")
    )
}
