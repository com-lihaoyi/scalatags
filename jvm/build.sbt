organization := "com.scalatags"

name := "scalatags"

scalaVersion := "2.10.0"

version := "0.3.0"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1" % "test"
)

unmanagedSourceDirectories in Compile <+= baseDirectory(_ / ".." / "src" / "main")

unmanagedSourceDirectories in Test <+= baseDirectory(_ / ".." / "src" / "test")


// Twirl
seq(Twirl.settings: _*)

sourceDirectory in Twirl.twirlCompile <<= (sourceDirectory in Test) / "twirl"

target in Twirl.twirlCompile <<= (sourceManaged in Test) / "generated-twirl-sources"

(sourceGenerators in Compile) ~= {x => Nil}

(sourceGenerators in Test) <+= Twirl.twirlCompile


// Sonatype
publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { (v: String) =>
  Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
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
    </developers>
  )