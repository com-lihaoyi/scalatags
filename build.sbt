organization  := "com.scalatags"

name          := "scalatags"

version       := "0.1.1"

scalaVersion  := "2.10.0"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10.0" % "2.0.M5" % "test"
)

publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
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
  </developers>)