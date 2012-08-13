resolvers := Seq(
  "Element Nexus" at "http://maven.element.hr/nexus/content/groups/public/"
, Resolver.url("Element Nexus (Ivy)",
    url("http://maven.element.hr/nexus/content/groups/public/"))(Resolver.ivyStylePatterns)
)

externalResolvers <<= resolvers map { rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false)
}

// =======================================================================================

// +-------------------------------------------------------------------------------------+
// | SBT Eclipse (https://github.com/typesafehub/sbteclipse)                             |
// | Creates .project and .classpath files for easy Eclipse project imports              |
// |                                                                                     |
// | See also: Eclipse downloads (http://www.eclipse.org/downloads/)                     |
// | See also: Scala IDE downloads (http://download.scala-ide.org/)                      |
// +-------------------------------------------------------------------------------------+

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")

// +-------------------------------------------------------------------------------------+
// | SBT Assembly (https://github.com/eed3si9n/sbt-assembly)                             |
// | Creates single jar releases from multiple projects                                  |
// +-------------------------------------------------------------------------------------+

// addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.3")
