import org.scalajs.dom.raw.HTMLTextAreaElement
import scala.scalajs.js

import org.scalajs.dom.document
import org.scalajs.dom.console


object Main {
  def main(args: Array[String]): Unit = {
    // import Mespeak._
    // loadConfig(MespeakConfig)
    // loadVoice(`en/en-us`)
    // speak("Hello, World")

    codemirror.CLike
    codemirror.Sublime
    import codemirror.EditorExtensions._

    val code = 
      """|val xs = List(1, 2, 3)
         |xs.reverse
         |""".stripMargin

    val options = js.Dictionary[Any](
      "autofocus" -> true,
      "mode" -> "text/x-scala",
      "theme" -> "solarized light",
      "keyMap" -> "sublime"
    ).asInstanceOf[codemirror.Options]

    val textArea = document.createElement("textarea").asInstanceOf[HTMLTextAreaElement]
    document.body.appendChild(textArea)

    val editor = 
      codemirror.CodeMirror.fromTextArea(
        textArea,
        options
      )

    editor.onKeyDown((editor, keyEvent) => {
      console.log(keyEvent)
      keyEvent.preventDefault()
    })

    editor.getDoc().setValue(code)
  }
}