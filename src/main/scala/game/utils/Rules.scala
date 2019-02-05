package game.utils

/**
 * Rules contains constants and values which define game processes. Can be modified by user.
 */
object Rules {

  /**
   * List of Goose points
   */
  val GEESE: Seq[Int] = Seq(5, 9, 14, 18, 23, 27)

  /**
   * The final point
   */
  val END: Int = 63

  /**
   * The Bridge start point
   */
  val BRIDGE_START: Int = 6

  /**
   * The Bridge end point
   */
  val BRIDGE_END: Int = 12

  /**
   * Minimum number of players
   */
  val PLAYERS_MIN = 2

  /**
   * Maximum number of players
   */
  val PLAYERS_MAX = 6

  /**
   * Represents the command to call about in menu
   */
  val ABOUT = "about"

  /**
   * Represents the command to call play in menu
   */
  val PLAY = "play"

  /**
   * Represents the command to call exit in menu
   */
  val EXIT = "exit"

  /**
   * Represents the command to call restart in menu
   */
  val RESTART = "restart"

  /**
   * Represents the "space" input
   */
  val SPACE = " "

  /**
   * Represents the "move" input
   */
  val MOVE = "move"

}
