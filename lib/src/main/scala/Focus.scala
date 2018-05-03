import scala.meta._

case class Pos(start: Int, end: Int)

class Focus(tree: Tree) {
  private var parent = List(tree)
  private var children = Vector(tree)
  private var child = List(0)
    
  private def toPos(pos: Position): Pos = Pos(pos.start, pos.end)

  def current: Pos = toPos(children(child.head).pos)

  def down: Pos = {
    if (children.nonEmpty) {
      parent = children(child.head) :: parent
      children = children(child.head).children.toVector
      child = 0 :: child
    }
    current
  }
  def up: Pos = {
    if (parent.size > 1) {
      parent = parent.tail
      children = Vector(parent.head)
      child = child.tail
    }
    current
  }
  def left: Pos = {
    if(child.head > 0) {
      child = (child.head - 1) :: child.tail
    }
    current
  }
  def right: Pos = {
    if (child.head < children.size - 1) {
      child = (child.head + 1) :: child.tail
    }
    current
  }

  // def find(tree: Tree, offset: Offset): Option[Tree] = {
  //   var found: Option[Tree] = None
  //   object findPos extends Traverser {
  //     override def apply(tree: Tree): Unit = {
  //       if (tree.pos.start <= offset.value &&
  //           offset.value <= tree.pos.end) {
  //         found = Some(tree)
  //         super.apply(tree)
  //       }
  //     }
  //   }
  //   findPos(tree)
  //   found
  // }
}