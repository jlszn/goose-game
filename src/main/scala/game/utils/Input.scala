package game.utils


sealed trait Input

case object About extends Input

case object Play extends Input

case object Space extends Input

case object MoveUser extends Input

case object BadUser extends Input

case object UnknownCommand extends Input


object InputMatcher {

  def getType(input: String, condition: Option[String] = None): Input = (input, condition) match {
    case ("about", None) => About
    case ("play", None) => Play
    case (" ", None) => Space
    case ("move", c) if c == condition => MoveUser
    case ("move", c) if c != condition => BadUser
    case _ => UnknownCommand
  }

}
