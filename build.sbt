organization := "hr.element.g4"

name := "g4"

version := "0.0.0"

crossScalaVersions := Seq("2.10.0-M6")

scalaVersion <<= (crossScalaVersions) { _.head }

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise") 

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)( _ :: Nil)

unmanagedSourceDirectories in Test := Nil

libraryDependencies ++= Seq(
)

resolvers := Seq("Element Nexus" at "http://maven.element.hr/nexus/content/groups/public")

externalResolvers <<= resolvers map { rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false)
}

publishTo := Some("Element Releases" at "http://maven.element.hr/nexus/content/repositories/releases/")

credentials += Credentials(Path.userHome / ".publish" / "element.credentials")

publishArtifact in (Compile, packageDoc) := false
