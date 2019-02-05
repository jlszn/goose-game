package game.utils

import game._
import game.utils.TextContainer._

/**
 * CommandsProcessor contains main methods for game controlling with some additional util methods.
 */
object CommandProcessor {

  def exit(): Unit = {
    println(BYE_MESSAGE)
    System.exit(0)
  }

  /**
   * A string used as the description of the project
   */
  val about: String =
    """
      |It's a Scala implementation of "The Goose Game Kata" (https://en.wikipedia.org/wiki/Game_of_the_Goose)
      |How to use:
      | 1. After you type "play" in the console, you will be asked to define the amount of users
      | 2. Write users' names in the console
      | 3. Write "move <some-player-name>
      | 4. Repeat until someone wins
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
  // takes an index of current user, adds 1 to it and divides by the size using mod.
    users.keysIterator.toList((users.keysIterator.indexOf(current) + 1) % users.size)

  /**
   * Method for processing initial console commands "about" and "play".
   */
  def initialCommandProcessing(): Unit = {
    //read command
    val input = InputMatcher.getInput.toLowerCase

    // print the about message and run again
    def printAbout(): Unit = {
      println(about)
      initialCommandProcessing() //restart an processing
    }

    // start registration and start a game
    def startGame(): Unit = {
      val users: Users = PlayerRegistrationUtil.register //runs the registration
      println(HOW_TO_START_MESSAGE)
      if (isStarted) {
        println(START_MESSAGE)
        GooseGame.play(users) // starts the game
      }
    }

    // print the message about unknown command and run initialization again
    def printUnknownCommand(): Unit = {
      println(UNKNOWN_COMMAND_MESSAGE)
      initialCommandProcessing() //restart the processing
    }

    // define a method according to user input
    InputMatcher.getType(input) match {
      case About => printAbout()
      case Play => startGame()
      case Exit => exit()
      case Restart => GooseGame.start()
      case _ => printUnknownCommand()
    }
  }

  /**
   * Method for checking if start game command was used after registration.
   *
   * @return true if command to start was given else false
   */
  def isStarted: Boolean = {
    val input = InputMatcher.getInput //read a command

    InputMatcher.getType(input) match {
      case Space => true // start a game
      case Exit => exit() // exit a game
        false
      case _ => println(START_HINT)
        isStarted // try again if there is no such command
    }

  }

}
