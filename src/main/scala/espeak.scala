package espeak

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSGlobalScope
object Espeak extends js.Object {
  def speak(
    text: String,
    amplitude: Int = js.native,
    pitch: Int = js.native,
    speed: Int = js.native,
    voice: String = js.native,
    wordgap: Int = js.native,
    noWorker: Boolean = js.native
  ): Unit = js.native
}

object Main {
  def main(args: Array[String]): Unit = {
    Espeak.speak("Hello, World")
  }
}