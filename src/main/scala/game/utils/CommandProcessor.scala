package game.utils

import game.{GooseGame, Users}

/**
 * CommandsProcessor contains main methods for game controlling with some additional util methods.
 */
object CommandProcessor {

  /**
   * A string used as the description of the project
   */
  val about: String =
    """
      |It's an Scala implementation of "The Goose Game Kata" (https://en.wikipedia.org/wiki/Game_of_the_Goose)
      |How to use:
      | 1. Define an amount of users and write it in a console.
      | 2. Write users' names in the console
      | 3. Write "move <some-player-name>
      | 4. Repeat until win
      |Rules:
      | During the game program will throw 2 dice every turn. Values of such dice define your future steps.
      | You will win if you reach the 63. But if you collect more than 63, you will be moved back on a difference between 63 and your current amount
      | If you step on a cell where another player is, this player will be sent on your previous position.
      | There are some zone with special abilities:
      |   1. The Bridge(6). When you reach it, you will be moved on 12.
      |   2. The Goose(5, 9, 14, 18, 23, 27). When you reach it, you will move again on the same step.
    """.stripMargin

  /**
   * This method is used for iterating over a map. It takes an index of current user,
   * adds 1 to it and divides by the size using mod.
   *
   * @param users   Current Users
   * @param current Current User
   * @return Next User
   */
  def nextUser(users: Users, current: String): String =
    users.keysIterator.toList((users.keysIterator.indexOf(current) + 1) % users.size)

  /**
   * Method for processing initial console commands "about" and "play".
   */
  def initialCommandProcessing(): Unit = {
    val initialInput = scala.io.StdIn.readLine()

    initialInput match {
      case "about" =>
        println(about)
        initialCommandProcessing()

      case "play" =>
        val users: Users = PlayerRegistrationUtil.register
        println("Push Space bar and Enter to start")
        if (isStarted) {
          println("Let's start!")
          GooseGame.play(users)
        }

      case _ =>
        println("Command not found.\nType 'about' to see the game rules, or skip to start users registration.")
        initialCommandProcessing()
    }
  }

  /**
   * Method for checking if start game command was used after registration.
   *
   * @return true if command to start was given else false
   */
  def isStarted: Boolean = {
    val startInput = scala.io.StdIn.readLine()

    if (startInput != " ") {
      println("Hint: to start a game - press Space bar and Enter")
      isStarted
    } else
      true

  }

}