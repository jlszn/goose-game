package game.utils

import game.utils.Rules._

import scala.util.matching.Regex

// types to represent input
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

/**
 * Util class to match input strings with types representing them
 **/
object InputMatcher {

  // convenience method to get input from command line
  def getInput: String = scala.io.StdIn.readLine()

  // pattern to match user input for setting number of players
  val Pattern: Regex = s"([$PLAYERS_MIN-$PLAYERS_MAX])".r

  def getType(input: String, condition: Option[String] = None): Input = (input, condition) match {
    case (ABOUT, None) => About
    case (PLAY, None) => Play
    case (EXIT, _) => Exit
    case (RESTART, _) => Restart
    case (SPACE, None) => Space
    case (_, Some(c)) if condition.contains(c) && input == s"$MOVE $c" => MoveUser
    case (_, Some(c)) if input == s"$MOVE $c" => BadUser
    case (Pattern(_), None) => RightPlayersNumber
    case _ => UnknownCommand
  }

}
