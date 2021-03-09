import mill._, scalalib._, scalajslib._, scalanativelib._, publish._

val dottyVersions = sys.props.get("dottyVersion").toList

val scalaVersions = "2.12.13" :: "2.13.4" :: "3.0.0-RC1" :: dottyVersions
val scala2Versions = scalaVersions.filter(_.startsWith("2."))

val scalaJSVersions = for {
  scalaV <- scala2Versions
  scalaJSV <- Seq("0.6.33", "1.4.0")
} yield (scalaV, scalaJSV)

val scalaNativeVersions = for {
  scalaV <- scala2Versions
  scalaNativeV <- Seq("0.4.0")
} yield (scalaV, scalaNativeV)

trait ScalatagsPublishModule extends PublishModule {
  def artifactName = "scalatags"

  def publishVersion = "0.9.3"

  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "com.lihaoyi",
    url = "https://github.com/lihaoyi/scalatags",
    licenses = Seq(License.MIT),
    scm = SCM(
      "git://github.com/lihaoyi/scalatags.git",
      "scm:git://github.com/lihaoyi/scalatags.git"
    ),
    developers = Seq(
      Developer("lihaoyi", "Li Haoyi", "https://github.com/lihaoyi")
    )
  )
}

trait Common extends CrossScalaModule {
  def isDotty = !crossScalaVersion.startsWith("2")
  def millSourcePath = super.millSourcePath / offset
  def ivyDeps = Agg(
    ivy"com.lihaoyi::sourcecode::0.2.3",
    ivy"com.lihaoyi::geny::0.6.5",
  )
  def compileIvyDeps = T {
    if (!isDotty) Agg(
      ivy"org.scala-lang:scala-reflect:${crossScalaVersion}",
    ) else Agg()
  }

  def offset: os.RelPath = os.rel
  def sources = T.sources(
    super.sources()
      .flatMap(source =>
        Seq(
          PathRef(source.path / os.up / source.path.last),
          PathRef(source.path / os.up / os.up / source.path.last),
        )
      )
  )
}

trait CommonTestModule extends ScalaModule with TestModule {
  def millSourcePath = super.millSourcePath / os.up
  def crossScalaVersion: String
  def ivyDeps = Agg(
    ivy"com.lihaoyi::utest::0.7.7",
    ivy"org.scala-lang.modules::scala-xml:2.0.0-M3"
  )
  def offset: os.RelPath = os.rel
  def testFrameworks = Seq("utest.runner.Framework")
  def sources = T.sources(
    super.sources()
      .++(CrossModuleBase.scalaVersionPaths(crossScalaVersion, s => millSourcePath / s"src-$s" ))
      .flatMap(source =>
        Seq(
          PathRef(source.path / os.up / "test" / source.path.last),
          PathRef(source.path / os.up / os.up / "test" / source.path.last),
        )
      )
      .distinct
  )
}


object scalatags extends Module {
  object jvm extends Cross[JvmScalatagsModule](scalaVersions:_*)
  class JvmScalatagsModule(val crossScalaVersion: String)
    extends Common with ScalaModule with ScalatagsPublishModule {

    def scalacOptions = Seq("-language:implicitConversions")

    object test extends Tests with CommonTestModule{
      def crossScalaVersion = JvmScalatagsModule.this.crossScalaVersion
    }
  }

  object js extends Cross[JSScalatagsModule](scalaJSVersions:_*)
  class JSScalatagsModule(val crossScalaVersion: String, crossJSVersion: String)
    extends Common with ScalaJSModule with ScalatagsPublishModule {
    def scalaJSVersion = crossJSVersion
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"org.scala-js::scalajs-dom::1.1.0"
    )
    def offset = os.up
    object test extends Tests with CommonTestModule{
      def offset = os.up
      def crossScalaVersion = JSScalatagsModule.this.crossScalaVersion
      val jsDomNodeJs =
        if(crossJSVersion.startsWith("0.6.")) Agg()
        else Agg(ivy"org.scala-js::scalajs-env-jsdom-nodejs:1.1.0")
      def ivyDeps = super.ivyDeps() ++ jsDomNodeJs
      def jsEnvConfig = mill.scalajslib.api.JsEnvConfig.JsDom()
    }
  }

  object native extends Cross[NativeScalatagsModule](scalaNativeVersions:_*)
  class NativeScalatagsModule(val crossScalaVersion: String, crossScalaNativeVersion: String)
    extends Common with ScalaNativeModule with ScalatagsPublishModule {
    def scalaNativeVersion = crossScalaNativeVersion
    def offset = os.up
    object test extends Tests with CommonTestModule{
      def offset = os.up
      def crossScalaVersion = NativeScalatagsModule.this.crossScalaVersion
    }
  }
}

object example extends ScalaJSModule{
  val (scalaV, scalaJSV) = scalaJSVersions.head
  def scalaVersion = scalaV
  def scalaJSVersion = scalaJSV
  def moduleDeps = Seq(scalatags.js(scalaV, scalaJSV))
}
