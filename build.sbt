enablePlugins(ScalaJSPlugin)

name := "Foodkeep"
scalaVersion := "2.13.1" // or any other Scala version >= 2.11.12
scalacOptions ++= Seq("-deprecation", "-feature")

// This is an application with a main method
scalaJSUseMainModuleInitializer := true
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

libraryDependencies ++= Seq(
    "org.scala-js" %%% "scala-js-macrotask-executor" % "1.0.0",
    "org.scala-js" %%% "scalajs-dom" % "2.1.0",
    // "com.lihaoyi" %%% "scalatags" % "0.8.2"
    ) 

