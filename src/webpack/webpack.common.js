const Path = require('path');
const Merge = require("webpack-merge");

const generatedConfig = require('./scalajs.webpack.config');

const rootDir = Path.resolve(__dirname, '../../../..');
console.log("***")
console.log(rootDir);
console.log("***")
console.log("***")
const resourcesDir = Path.resolve(rootDir, 'src/main/resources');
console.log("***")
console.log("***")
console.log(resourcesDir);
console.log("***")
console.log("***")

const ScalaJs = Merge(generatedConfig, {
  resolve: {
    alias: {
      'resources': resourcesDir
    }
  },
  module: {
    rules: [
      {
        test: /\.png$/,
        loader: 'file-loader',
        options: {
          name: "[name].[hash].[ext]"
        }
      }
    ]
  }
});

const Web = {
  devtool: "source-map",
  resolve: {
    alias: {
      'resources': resourcesDir,
      'node_modules': Path.resolve(__dirname, 'node_modules')
    }
  },
  module: {
    rules: [
      {
        test: /\.(png|svg|woff|woff2|eot|ttf)$/,
        loader: 'file-loader',
        options: {
          name: "[name].[hash].[ext]"
        }
      }
    ]
  }
}

module.exports = {
  rootDir: rootDir,
  resourcesDir: resourcesDir,
  Web: Web,
  ScalaJs: ScalaJs
}