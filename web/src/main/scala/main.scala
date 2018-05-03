import org.scalajs.dom.{document, console, window}
import org.scalajs.dom.raw.HTMLTextAreaElement
import org.scalajs.dom.ext.KeyCode

import scala.scalajs.js

import codemirror.{Position => CMPos, _}

import scala.meta._

object Main {
  def main(args: Array[String]): Unit = {
    // import Mespeak._
    // loadConfig(MespeakConfig)
    // loadVoice(`en/en-us`)
    // speak("Hello, World")

    CLike
    Sublime

    import EditorExtensions._

    val code = Example.code2

    val isMac = window.navigator.userAgent.contains("Mac")
    val ctrl = if (isMac) "Cmd" else "Ctrl"

    CodeMirror.keyMap.sublime -= "Ctrl-L"

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

    val tree = code.parse[Source].get
    val focus = new Focus(tree)

    def setSel(pos: Pos): Unit = {
      val doc = editor.getDoc()

      val start = doc.posFromIndex(pos.start)
      val end = doc.posFromIndex(pos.end)

      doc.setSelection(start, end)
      editor.scrollIntoView(start, 10)
    }
    setSel(focus.current)

    editor.onKeyDown((editor, keyEvent) => {
      val keyCode = keyEvent.keyCode
      val pos = 
        keyCode match {
          case KeyCode.Down => 
            keyEvent.preventDefault()
            focus.down
          case KeyCode.Up =>
            keyEvent.preventDefault()
            focus.up
          case KeyCode.Left => 
            keyEvent.preventDefault()
            focus.left
          case KeyCode.Right =>
            keyEvent.preventDefault()
            focus.right
          case _ =>
            focus.current
        }
      setSel(pos)
    })

    editor.getDoc().setValue(code)
  }
}