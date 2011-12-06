name := "Scala Pygments"

version := "1.0"

scalaVersion := "2.9.1"

resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
                    "releases"  at "http://scala-tools.org/repo-releases")

libraryDependencies ++= Seq(
	"org.apache.commons" % "commons-compress" % "1.3",
	"commons-io" % "commons-io" % "2.1",
	"org.specs2" %% "specs2" % "1.6.1",
    "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test"
)