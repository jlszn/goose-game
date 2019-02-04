import GooseGame.Users

object CommandsProcessor {

  val about: String =
    """
      |It's an Scala implementation of "The Goose Game Kata" (https://en.wikipedia.org/wiki/Game_of_the_Goose)
      |How to use:
      | 1. Define an amount of users and write it in a console.
      | 2. Write users' names in the console
      | 3. Write "move <some-player-name>
      | 4. Repeat until win
      |Rules:
      | During the game program will throw 2 dices every turn. Values of such dices define your future steps.
      | You will win if you reach the 63. But if you collect more than 63, you will be moved back on a difference between 63 and your current amount
      | If you step on a cell where another player is, this player will be sent on your previous position.
      | There are some zone with special abilities:
      |   1. The Bridge(6). When you reach it, you will be moved on 12.
      |   2. The Goose(5, 9, 14, 18, 23, 27). When you reach it, you will move again on the same step.
    """.stripMargin

  val showRules: String = {
    """
      |GAME RULES:
      |Before starting game you need to register users for playing.
      |For start registration process - put 'play' to console.
      |Then - you need to set number of players and names for players.
    """.stripMargin
  }

  def nextUser(users: Users, current: String): String = {
    users.keysIterator.toList(users.keysIterator.indexOf(current) + 1 % users.size)
  }

  def initialCommandsProcessing(): Unit = {
    val initialInput = scala.io.StdIn.readLine()

    initialInput match {
      case "about" =>
        println(about)
        initialCommandsProcessing()

      case "play" =>
        val users: Map[String, Int] = PlayerRegistrationUtil.register
        println("Push Space bar and Enter to start")
        if (isStarted) {
          println("Let's start!")
          GooseGame.play(users)
        }

      case _ =>
        println("Command not found.")
        println("Type 'about' to see the game rules, or skip to start users registration.")
        initialCommandsProcessing()

    }

  }

  //check start button pressed
  def isStarted: Boolean = {
    val startInput = scala.io.StdIn.readLine()

    if (startInput != " ") {
      println("Hint: for start a game - press Space bar and Enter")
      isStarted
    } else {
      true
    }
  }

}