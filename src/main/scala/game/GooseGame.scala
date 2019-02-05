package game

import game.utils.Rules._
import game.utils.{CommandProcessor, RandomUtil}

/**
  * The main class. GooseGame contains methods used for game process controlling.
  */
object GooseGame extends App {

  /**
    * Util method that starts an application.
    */
  def start(): Unit = {

    println("Welcome to the Game of the Goose!")
    println(
      "Type 'about' to see the game rules\n" +
        "Type 'play' to start users registration."
    )

    CommandProcessor.initialCommandProcessing()
  }

  /**
    * Util method represents a 2 dice roll for a some user. It includes command validation too.
    * @param turnOf Current user
    * @return 2 dice roll for a user
    */
  def roll(turnOf: String): (Int, Int) = {

    scala.io.StdIn.readLine().trim.split(" ") match {
      case Array("move", user) if user == turnOf =>
        val dice = RandomUtil.roll()
        println(s"$turnOf rolled dice: " + dice._1 + ", " + dice._2)
        dice
      case Array("move", user) if user != turnOf =>
        println(s"""Wrong user, try "move $turnOf"""")
        roll(turnOf)
      case _ =>
        println(s"""Wrong command, try "move $turnOf"""")
        roll(turnOf)
    }

  }

  /**
    * Method that represents a single move. It contains a set of additional internal methods.
    * @param user Current user
    * @param diceSum Sum of 2 roll dices
    * @param users A collection of all users
    * @return A tuple of (String, Map[String, Int]) type. String represents a message got during a move.
    *         Map[String, Int] represents an updated list of users that will be used during next turn.
    */
  def move(user: String, diceSum: Int, users: Users): (String, Users) = {

    // handle emptiness
    // switch to .fold
    val currentPosition: Int = users(user)

    def zero: String = if (currentPosition == 0) "the Start" else s"$currentPosition"

    // messages
    def message(position: String): String =
      s"$user moves from $zero to $position"

    def bouncesMessage(position: Int): String =
      message(s"$END") + s".\n$user bounces! $user returns to ${2 * END - position}"

    def gooseM(position: Int): String =
      s", The Goose.\n$user moves again and goes to ${position + diceSum}"

    def gooseMessage(position: Int): String =
      message(s"$position" + gooseM(position))

    def doubleGoose(position: Int): String =
      gooseMessage(position) + gooseM(position + diceSum)

    def prankMessage(user: (String, Int), addition: Option[String] = None): String = addition match {
      case Some(a) => a + s".\nOn ${user._2} there is ${user._1}, who returns to $zero."
      case _ => s".\nOn ${user._2} there is ${user._1}, who returns to $currentPosition"
    }

    def prankMove(message: String, pair: (String, Int)): (String, Users) = {
      val newUsers = users + (pair._1 -> currentPosition) + (user -> pair._2)
      (prankMessage(pair, Some(message)), newUsers)
    }

    def prankOrMove(position: Int, message: String, bonus: Int = 0): (String, Users) =
      users.find(_._2 == position + bonus) match {
        case Some(pair) => prankMove(message, pair)
        case _ => (message, users + (user -> (position + bonus)))
      }

    diceSum + currentPosition match {
      case a if a > END => (bouncesMessage(a), users + (user -> (2 * END - a)))
      case END => (message(s"$END") + s".\n$user Wins!!", users + (user -> END))
      case BRIDGE_START => prankOrMove(BRIDGE_END, message(s"The Bridge.\n$user jumps to $BRIDGE_END."))
      case a if GEESE.contains(a) && GEESE.contains(a + diceSum) => prankOrMove(a + diceSum, doubleGoose(a), diceSum)
      case a if GEESE.contains(a) => prankOrMove(a, gooseMessage(a), diceSum)
      case a => prankOrMove(a, message(s"$a"))
    }
  }

  // before each round, output where everyone is standing

  /**
    * Util method that starts a game.
    * @param users A collection of available users.
    */
  def play(users: Users): Unit = {

    def playRound(turnOf: String, users: Users): Unit =
      if (users.exists(_._2 == END)) println("Game over")
      else {
        val dice: (Int, Int) = roll(turnOf)

        def moveThis: (String, Users) = move(turnOf, dice._1 + dice._2, users)

        def movedUsers: Users = {
          val moved = moveThis
          println(moved._1)
          moved._2
        }

        val nextUser: String = CommandProcessor.nextUser(users, turnOf)

        playRound(nextUser, movedUsers)
      }

    playRound(RandomUtil.selectFirst(users), users)
  }

  start()

}