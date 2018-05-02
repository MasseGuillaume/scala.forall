import org.scalajs.dom.raw.HTMLTextAreaElement
import scala.scalajs.js

import org.scalajs.dom.{document, console, window}

import codemirror._


object Main {
  def main(args: Array[String]): Unit = {
    // import Mespeak._
    // loadConfig(MespeakConfig)
    // loadVoice(`en/en-us`)
    // speak("Hello, World")

    CLike
    Sublime

    import EditorExtensions._

    val code = Example.code

    val isMac = window.navigator.userAgent.contains("Mac")
    val ctrl = if (isMac) "Cmd" else "Ctrl"

    CodeMirror.keyMap.sublime.delete("Ctrl-L")

    val darkTheme = "solarized dark"
    val lightTheme = "solarized light"

    val options = js.Dictionary[Any](
      "autofocus" -> true,
      "mode" -> "text/x-scala",
      "theme" -> lightTheme,
      "keyMap" -> "sublime",
      "extraKeys" -> js.Dictionary(
        "scrollPastEnd" -> false,
        "F2" -> "toggleSolarized",
        s"$ctrl-B" -> "browse",
        "Tab" -> "defaultTab"
      )
    ).asInstanceOf[codemirror.Options]

    CodeMirror.commands.toggleSolarized = (editor: Editor) => {
      val key = "theme"

      val currentTheme = editor.getOption(key).asInstanceOf[String]
      val nextTheme = 
        if(currentTheme == darkTheme) lightTheme
        else darkTheme

      editor.setOption(key, nextTheme)
    }

    val textArea = document.createElement("textarea").asInstanceOf[HTMLTextAreaElement]
    document.body.appendChild(textArea)

    val editor = 
      CodeMirror.fromTextArea(
        textArea,
        options
      )

    editor.onKeyDown((editor, keyEvent) => {
      console.log(keyEvent)
      // keyEvent.preventDefault()
    })

    editor.getDoc().setValue(code)
  }
}