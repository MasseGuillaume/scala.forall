package codemirror

import org.scalajs.dom.raw.{Event, Element, HTMLElement, HTMLTextAreaElement}

import scala.scalajs.js
import js.annotation._
import js.{Dictionary, RegExp, UndefOr, |}

@js.native
@JSImport("codemirror", JSImport.Namespace)
object CodeMirror extends js.Object {
  def fromTextArea(textarea: HTMLTextAreaElement, options: Options): TextAreaEditor = js.native
}

trait TextAreaEditor extends Editor {
  def save(): Unit
  def toTextArea(): Unit
  def getTextArea: HTMLTextAreaElement
}

trait Options extends js.Object {
  val autofocus: UndefOr[Boolean]
  val extraKeys: UndefOr[Dictionary[String]]
  val indentWithTabs: UndefOr[Boolean]
  val keyMap: UndefOr[String]
  val lineNumbers: UndefOr[Boolean]
  val lineWrapping: UndefOr[Boolean]
  val mode: UndefOr[String | js.Object]
  val scrollbarStyle: UndefOr[String]
  val showCursorWhenSelecting: UndefOr[Boolean]
  val smartIndent: UndefOr[Boolean]
  val tabindex: UndefOr[Int]
  val tabSize: UndefOr[Int]
  val theme: UndefOr[String]
}

trait Editor extends js.Object

@JSImport("codemirror/mode/clike/clike", JSImport.Namespace)
@js.native
object CLike extends js.Object
