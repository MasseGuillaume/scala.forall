import org.scalajs.dom.raw.HTMLTextAreaElement
import scala.scalajs.js

import org.scalajs.dom.document


object Main {
  def main(args: Array[String]): Unit = {
    // import Mespeak._
    // loadConfig(MespeakConfig)
    // loadVoice(`en/en-us`)
    // speak("Hello, World")

    codemirror.CLike

    val options = js.Dictionary[Any](
      "mode" -> "text/x-scala"
    ).asInstanceOf[codemirror.Options]

    val textArea = document.createElement("textarea").asInstanceOf[HTMLTextAreaElement]
    document.body.appendChild(textArea)

    codemirror.CodeMirror.fromTextArea(
      textArea,
      options
    )
  }
}