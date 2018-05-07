import scala.meta._

case class Pos(start: Int, end: Int)

object Focus {
  def apply(tree: Tree): Focus = {
    new Focus(parents = List(tree), children = Vector(tree), child = List(0))
  }
}

case class Focus private(var parents: List[Tree], var children: Vector[Tree], var child: List[Int]) {

  private def toPos(pos: Position): Pos = Pos(pos.start, pos.end)

  def current: Pos = 
    toPos(children(child.head).pos)

  def down(): Focus = {
    val currentParent = children(child.head)
    val newChildrens = getChildren(currentParent)
    if (newChildrens.nonEmpty) {
      parents = currentParent :: parents
      child = 0 :: child
      children = newChildrens
    }
    this
  }
  private def showPos(tree: Tree): (Int, Int) = {
    (tree.pos.start, tree.pos.end)
  }
  def up(): Focus = {
    if (parents.size > 1) {
      parents = parents.tail
      val currentParent = parents.head
      child = child.tail
      children = getChildren(currentParent)
    }
    this
  }
  private def getChildren(tree: Tree): Vector[Tree] = {
    tree match {
      case _: Type.Name => {
        val parent = tree.parent.get
        println(parent.getClass)

        parent match {
          case cls: Defn.Class => cls.templ.stats.toVector
          case _ => Vector()
        }
      }
      case _ => 
        tree.children.toVector.filter(_.tokens.nonEmpty)
    }
    
  }

  def left(): Focus = {
    if(child.head > 0) {
      child = (child.head - 1) :: child.tail
    }
    this
  }
  def right(): Focus = {
    if (child.head < children.size - 1) {
      child = (child.head + 1) :: child.tail
    }
    this
  }
}