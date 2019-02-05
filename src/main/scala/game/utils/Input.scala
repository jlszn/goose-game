package game.utils

import scala.util.matching.Regex


sealed trait Input

case object About extends Input

case object Play extends Input

case object Exit extends Input

case object Restart extends Input

case object Space extends Input

case object MoveUser extends Input

case object BadUser extends Input

case object RightPlayersNumber extends Input

case object UnknownCommand extends Input


object InputMatcher {

  def getInput: String = scala.io.StdIn.readLine()

  val Pattern: Regex = "([2-6])".r

  def getType(input: String, condition: Option[String] = None): Input = (input, condition) match {
    case ("about", None) => About
    case ("play", None) => Play
    case ("exit", _) => Exit
    case ("restart", _) => Restart
    case (" ", None) => Space
    case (_, Some(c)) if condition.contains(c) && input == s"move $c" => MoveUser
    case (_, Some(c)) if input == s"move $c" => BadUser
    case (Pattern(_), None) => RightPlayersNumber
    case _ => UnknownCommand
  }

}
