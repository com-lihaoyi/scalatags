
lazy val readme = scalatex.ScalatexReadme(
  projectId = "readme",
  wd = file(""),
  url = "https://github.com/lihaoyi/scalatags/tree/master",
  source = "Readme",
  autoResources = Seq("Autocomplete.png", "ErrorHighlighting.png", "InlineDocs.png", "out.js")
).settings(
  scalaVersion := "2.12.8",
  (unmanagedSources in Compile) += baseDirectory.value/".."/"project"/"Constants.scala",
  (resources in Compile) += (baseDirectory.value/".."/"out"/"example"/"fullOpt"/"dest"/"out.js"),
)
