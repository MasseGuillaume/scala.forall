import scala.meta._

object FocusSuite extends FunSuite {
  test("current") {
    doFocus(
      noop,
      """|class A
         |class B {
         |  val c = 1
         |}""".stripMargin,
      """|→class A
         |class B {
         |  val c = 1
         |}←""".stripMargin,
    )
  }
  test("down") {
    doFocus(
      _.down,
      """|class A
         |class B {
         |  val c = 1
         |}""".stripMargin,
      """|→class A←
         |class B {
         |  val c = 1
         |}""".stripMargin,
    )
  }
  test("down right") {
    doFocus(
      _.down.right,
      """|class A
         |class B {
         |  val c = 1
         |}""".stripMargin,
      """|class A
         |→class B {
         |  val c = 1
         |}←""".stripMargin
    )
  }
  test("preserve children position") {
    doFocus(
      _.down.right.down.up,
      """|class A
         |class B {
         |  val c = 1
         |}""".stripMargin,
      """|class A
         |→class B {
         |  val c = 1
         |}←""".stripMargin
    )
  }
  test("no children") {
    doFocus(_.down.down.down, "class A", "class →A←")
  }
  
  private def noop: Focus => Unit = _ => ()
  private def doFocus(f: Focus => Unit, code: String, annotedSource: String): Unit = {
    println(code)
    val tree = code.parse[Source].get
    val focus = Focus(tree)
    f(focus)
    val obtained = focus.current
    val expected = selection(annotedSource)
    assert(obtained == expected)
  }
  private def selection(annotedSource: String): Pos = {
    val nl = "\n"
    val startMarker = '→'
    val stopMarker = '←'

    var i = 0
    var markersBuilder: Option[Pos] = None
    var lastStart: Option[Int] = None
    def error(msg: String, pos: Int): Unit = {
      sys.error(
        msg + nl +
          annotedSource + nl +
          (" " * pos) + "^"
      )
    }
    annotedSource.foreach { c =>
      if (c == startMarker) {
        if (lastStart.nonEmpty)
          error(s"Missing closing marker: '$stopMarker'", i)
        lastStart = Some(i)
      } else if (c == stopMarker) {
        lastStart match {
          case Some(start) => markersBuilder = Some(Pos(start, i - 1))
          case None => error("Unexpected closing marker", i)
        }
        lastStart = None
      }
      i += 1
    }

    markersBuilder.getOrElse(throw new Exception("cannot find selection"))
  }
}