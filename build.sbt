lazy val webpackDir = Def.setting { (baseDirectory in ThisProject).value / "src" / "webpack" }
lazy val webpackDevConf = Def.setting { Some(webpackDir.value / "webpack-dev.config.js") }
lazy val webpackProdConf = Def.setting { Some(webpackDir.value / "webpack-prod.config.js") }

lazy val demo = project.in(file("."))
  .settings(
    scalaVersion := "2.12.5",
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",

    useYarn := true,
    version in webpack := "3.5.5",
    version in startWebpackDevServer := "2.7.1",
    webpackConfigFile in fastOptJS := webpackDevConf.value,
    webpackConfigFile in fullOptJS := webpackProdConf.value,
    webpackMonitoredDirectories += (resourceDirectory in Compile).value,
    includeFilter in webpackMonitoredFiles := "*",
    webpackResources := webpackDir.value * "*.js",
    webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
    webpackBundlingMode in fullOptJS := BundlingMode.Application,
    npmDependencies in Compile ++= Seq(
      "mespeak" -> "2.0.2"
    ),
    npmDevDependencies in Compile ++= Seq(
      "compression-webpack-plugin" -> "1.0.0",
      "clean-webpack-plugin" -> "0.1.16",
      "css-loader" -> "0.28.5",
      "extract-text-webpack-plugin" -> "3.0.0",
      "file-loader" -> "0.11.2",
      "html-webpack-plugin" -> "2.30.1",
      "node-sass" -> "4.5.3",
      "resolve-url-loader" -> "2.1.0",
      "sass-loader" -> "6.0.6",
      "style-loader" -> "0.18.2",
      "uglifyjs-webpack-plugin" -> "0.4.6",
      "webpack-merge" -> "4.1.0"
    ),
    scalaJSUseMainModuleInitializer := true
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)