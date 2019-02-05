package game.utils

import game.utils.Rules._
import game.utils.TextContainer._

import scala.util.matching.Regex

/**
 * Types to represent input
 **/
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

  /**
   * Convenience method to get input from command line
   *
   * @return String - input string
   **/
  def getInput: String = scala.io.StdIn.readLine()

  /**
   * Pattern to match user input for setting number of players
   *
   * @return Regex - regex, that considers rules about players amount to match the input on
   **/
  private val Pattern: Regex = s"([$PLAYERS_MIN-$PLAYERS_MAX])".r

  /**
   * Util method for matching the input with case objects, that represent it
   *
   * @return Input -representing the input type
   **/
  def getType(input: String, condition: Option[String] = None): Input = (input, condition) match {
    case (ABOUT, None) => About
    case (PLAY, None) => Play
    case (EXIT, _) => Exit
    case (RESTART, _) => Restart
    case (SPACE, None) => Space
    case (_, Some(c)) if condition.contains(c) && input == s"$MOVE $c" => MoveUser // if user called move directive right
    case (_, Some(c)) if input == s"$MOVE $c" => BadUser // if user misspelled player's name
    case (Pattern(_), None) => RightPlayersNumber // if user input amount of players that passes the rules
    case _ => UnknownCommand
  }

}
