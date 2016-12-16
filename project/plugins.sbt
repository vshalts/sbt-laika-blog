resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("org.scalariform"    % "sbt-scalariform"     % "1.6.0")
addSbtPlugin("io.get-coursier"    % "sbt-coursier"        % "1.0.0-M15")

libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value
