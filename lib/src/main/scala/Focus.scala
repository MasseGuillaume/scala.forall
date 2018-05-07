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
    def default = tree.children.toVector.filter(_.tokens.nonEmpty)
    
    tree.parent match {
      case Some(parent) => {
        tree match {          
          case _: Term.Name => {
            parent match {
              case t: Defn.Object => t.templ.stats.toVector
              case t: Pkg.Object => t.templ.stats.toVector
              case _ => default
            }
          }
          case _: Type.Name => {
            parent match {
              case t: Defn.Class => t.templ.stats.toVector
              case t: Defn.Trait => t.templ.stats.toVector
              case _ => default
            }
          }
          case _: Term.Select => {
            default
          }
          case _: Term.Apply => {
            default
          }
          case _ => default
        }
      }
      case _ => default
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