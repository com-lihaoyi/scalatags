import mill.define.Target
import mill._, scalalib._, scalajslib._, scalanativelib._, publish._
import $ivy.`com.lihaoyi::mill-contrib-buildinfo:$MILL_VERSION`
import mill.contrib.buildinfo._
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version_mill0.9:0.1.1`
import de.tobiasroeser.mill.vcs.version.VcsVersion
import $ivy.`com.github.lolgab::mill-mima_mill0.9:0.0.6`
import com.github.lolgab.mill.mima._
import mill.scalalib.api.Util.isScala3

val scala2Versions = List("2.12.13", "2.13.4")
val scala3Versions = List("3.0.2")
val scalaVersions = scala2Versions ++ scala3Versions

val scalaJSVersions = for {
  scalaV <- scalaVersions
  scalaJSV <- Seq("0.6.33", "1.5.1")
  if scalaJSV.startsWith("1.")
} yield (scalaV, scalaJSV)

val scalaNativeVersions = for {
  scalaV <- scala2Versions
  scalaNativeV <- Seq("0.4.0")
} yield (scalaV, scalaNativeV)

trait ScalatagsPublishModule extends PublishModule with Mima {
  def artifactName = "scalatags"

  def publishVersion = VcsVersion.vcsState().format()

  def mimaPreviousVersions = Seq("0.10.0")

  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "com.lihaoyi",
    url = "https://github.com/lihaoyi/scalatags",
    licenses = Seq(License.MIT),
    versionControl = VersionControl(
      browsableRepository = Some("git://github.com/lihaoyi/scalatags.git"),
      connection = Some("scm:git://github.com/lihaoyi/scalatags.git")
    ),
    developers = Seq(
      Developer("lihaoyi", "Li Haoyi", "https://github.com/lihaoyi")
    )
  )

  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"com.lihaoyi::pprint::0.6.6"
  )
}

trait Common extends CrossScalaModule {
  def millSourcePath = super.millSourcePath / offset
  def ivyDeps = Agg(
    ivy"com.lihaoyi::sourcecode::0.2.7",
    ivy"com.lihaoyi::geny::0.6.10",
  )
  def compileIvyDeps = T { super.compileIvyDeps() ++
    (
      if (isScala3(crossScalaVersion)) Agg()
      else Agg(
        ivy"org.scala-lang:scala-reflect:${scalaVersion()}"
      )
    )
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

trait CommonTestModule extends ScalaModule with TestModule.Utest with BuildInfo {
  def millSourcePath = super.millSourcePath / os.up
  def crossScalaVersion: String
  val scalaXmlVersion = if(crossScalaVersion.startsWith("2.11.")) "1.3.0" else "2.0.1"
  def ivyDeps = Agg(
    ivy"com.lihaoyi::utest::0.7.10",
    ivy"org.scala-lang.modules::scala-xml:$scalaXmlVersion"
  )
  def offset: os.RelPath = os.rel
  def sources = T.sources(
    super.sources()
      .flatMap(source =>
        Seq(
          PathRef(source.path / os.up / "test" / source.path.last),
          PathRef(source.path / os.up / os.up / "test" / source.path.last),
        )
      )
      .distinct
  )
  override def buildInfoPackageName = Some("scalatags")
  override def buildInfoMembers = Map(
    "scalaMajorVersion" -> crossScalaVersion.split('.').head
  )
}


object scalatags extends Module {
  object jvm extends Cross[JvmScalatagsModule](scalaVersions:_*)
  class JvmScalatagsModule(val crossScalaVersion: String)
    extends Common with ScalaModule with ScalatagsPublishModule {

    object test extends Tests with CommonTestModule{
      def crossScalaVersion = JvmScalatagsModule.this.crossScalaVersion
    }
  }

  object js extends Cross[JSScalatagsModule](scalaJSVersions:_*)
  class JSScalatagsModule(val crossScalaVersion: String, crossJSVersion: String)
    extends Common with ScalaJSModule with ScalatagsPublishModule {
    def scalaJSVersion = crossJSVersion
    def ivyDeps = super.ivyDeps() ++ (
      if(crossJSVersion.startsWith("0.6.")) Agg(ivy"org.scala-js::scalajs-dom::1.1.0")
      else Agg(ivy"org.scala-js::scalajs-dom::2.0.0")
    )
    def offset = os.up
    object test extends Tests with CommonTestModule{
      def offset = os.up
      def crossScalaVersion = JSScalatagsModule.this.crossScalaVersion
      val jsDomNodeJs =
        if(crossJSVersion.startsWith("0.6.")) Agg()
        else Agg(ivy"org.scala-js::scalajs-env-jsdom-nodejs:1.1.0").map(_.withDottyCompat(crossScalaVersion))
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
