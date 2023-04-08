lazy val readme = scalatex.ScalatexReadme(
  projectId = "readme",
  wd = file(""),
  url = "https://github.com/lihaoyi/scalatags/tree/master",
  source = "Readme",
  autoResources =
    Seq("Autocomplete.png", "ErrorHighlighting.png", "InlineDocs.png", "example-opt.js")
).settings(
  scalaVersion := "2.12.8",
  (unmanagedSources in Compile) += baseDirectory.value / ".." / "project" / "Constants.scala",
  (resources in Compile) += (fullOptJS in (example, Compile)).value.data,
  (resources in Compile) += (doc in (scalatagsJS, Compile)).value,
  (run in Compile) := (run in Compile).dependsOn(Def.task {
    sbt.IO.copyDirectory(
      (doc in (scalatagsJS, Compile)).value,
      (target in Compile).value / "scalatex" / "api",
      overwrite = true
    )
  }).evaluated
)
