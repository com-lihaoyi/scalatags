import mill._, scalalib._, scalajslib._, scalanativelib._, publish._


trait ScalatagsPublishModule extends PublishModule {
  def artifactName = "scalatags"

  def publishVersion = "0.8.6"

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
  def millSourcePath = super.millSourcePath / offset
  def ivyDeps = Agg(
    ivy"com.lihaoyi::sourcecode::0.2.1",
    ivy"com.lihaoyi::geny::0.5.1",
  )
  def compileIvyDeps = Agg(
    ivy"org.scala-lang:scala-reflect:${scalaVersion()}",
  )
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
    ivy"com.lihaoyi::utest::0.7.4",
    ivy"org.scala-lang.modules::scala-xml:${if(scalaVersion().startsWith("2.11.")) "1.2.0" else "2.0.0-M1"}",
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
  object jvm extends Cross[JvmScalatagsModule]("2.12.10", "2.13.1")
  class JvmScalatagsModule(val crossScalaVersion: String)
    extends Common with ScalaModule with ScalatagsPublishModule {

    object test extends Tests with CommonTestModule{
      def crossScalaVersion = JvmScalatagsModule.this.crossScalaVersion
    }
  }

  object js extends Cross[JSScalatagsModule](("2.12.10", "0.6.32"), ("2.13.1", "0.6.32"), ("2.12.10", "1.0.0"), ("2.13.1", "1.0.0"))
  class JSScalatagsModule(val crossScalaVersion: String, crossJSVersion: String)
    extends Common with ScalaJSModule with ScalatagsPublishModule {
    def scalaJSVersion = crossJSVersion
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"org.scala-js::scalajs-dom::1.0.0"
    )
    def offset = os.up
    object test extends Tests with CommonTestModule{
      def offset = os.up
      def crossScalaVersion = JSScalatagsModule.this.crossScalaVersion
      def jsEnvConfig = mill.scalajslib.api.JsEnvConfig.Phantom("phantomjs", Nil, Map.empty, false)
    }
  }

  object native extends Cross[NativeScalatagsModule](("2.11.12", "0.3.9"), ("2.11.12", "0.4.0-M2"))
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
  def scalaVersion = "2.12.10"
  def scalaJSVersion = "0.6.32"
  def moduleDeps = Seq(scalatags.js("2.12.10", "0.6.32"))
}
