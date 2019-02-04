

object GooseGame /* extends App*/ {

  type Users = Map[String, Int]

  // (un)expected behaviour to handle:

  // check if name doesn't yet exist
  // after two users are registered do not accept any more of them. If input is any other than Space, give user a hint
  // check if moving input is correct, else give a hint: "To move a player type "Move name""
  // check if "move $name" is a registered name
  // check if it's the turn of the user from the input, else give user a hint
  // add comments

  // additional features if we still have time tomorrow:

  // provide a way to finish the game anytime - ask if the user wants to restart or just quit the app
  // provide a game description/rules on "about" input
  // provide a way to change names
  // tests
  // randomize who would be the first to roll
  // support from 2 to 6 players

  // validate with Eithers?

  // Игорь
  // returns two registered users in the end, until then side-effects with printlns
  def register: (String, String) = ("", "")

  def start(): Unit = {

    println("Welcome to Goose Game!")

    val users: (String, String) = register

    println("Let's start!")

    //play(users)
  }

  // Вадим
  // returns values from both dice
  def roll(turnOf: String, users: Users): (Int, Int) = {
    val input = scala.io.StdIn.readLine()

    if (input == s"$turnOf roll") (6, 6) else {
      println("Wrong directive")
      roll(turnOf, users)
    }
  }

  /*def play(users: (String, String), positions: (Int, Int) = (0, 0)): Unit = {

    val user1: String = users._1
    val user2: String = users._2

    def playRound(turnOf: String, users: Users): Unit =
      if (users.exists(_._2 == 63)) println("Game over")
      else {
        val dice: (Int, Int) = roll(turnOf, users)

        def moveThis(oldPosition: Int): (String, Users) = move(turnOf, dice._1 + dice._2, oldPosition, Map())

        def newPosition(oldPosition: Int): Users = {
          val moved = moveThis(oldPosition)
          println(moved._1)
          moved._2
        }

        val isTurnOfUser1 = turnOf == user1

        val nextUser: String = if (isTurnOfUser1) user2 else user1
        val updatePosition: Users = if (isTurnOfUser1) newPosition(position1) else newPosition(position2)

        if (isTurnOfUser1) playRound(nextUser, updatePosition, position2)
        else playRound(nextUser, position1, updatePosition)

      }

    else playRound(user1)
  }*/


  // returns new position and message
  // $user moves from $currentPosition to $newPosition"
  // The Goose message
  // The Bridge message
  // etc

  val geese: Seq[Int] = Seq(5, 9, 14, 18, 23, 27)


  def move(user: String, diceSum: Int, users: Users): (String, Users) = {

    // handle emptiness
    // switch to .fold
    val currentPosition: Int = users(user)

    def zero: String = if (currentPosition == 0) "the Start" else s"$currentPosition"

    // messages
    def message(position: String): String =
      s"$user moves from $zero to $position.\n"

    def bouncesMessage(position: Int): String =
      message("63") + s". $user bounces! $user returns to ${63 - (position - 63)}"

    def gooseM(position: Int): String =
      s", The Goose.\n$user moves again and goes to ${position + diceSum}"

    def gooseMessage(position: Int): String =
      message(s"$position" + gooseM(position))

    def doubleGoose(position: Int): String =
      gooseMessage(position) + gooseM(position + diceSum)

    def prankMessage(user: (String, Int), addition: Option[String] = None): String = addition match {
      case Some(a) => a + s"On ${user._2} there is ${user._1}, who returns to $zero."
      case _ => s"On ${user._2} there is ${user._1}, who returns to $currentPosition"
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
      case a if a > 63 => (bouncesMessage(a), users + (user -> (a - 63)))
      case 63 => (message("63") + s"$user Wins!!", users + (user -> 63))
      case 6 => prankOrMove(12, message(s"The Bridge. $user jumps to 12.\n"))
      case a if geese.contains(a) && geese.contains(a + diceSum) => prankOrMove(a + diceSum, doubleGoose(a), diceSum)
      case a if geese.contains(a) => prankOrMove(a, gooseMessage(a), diceSum)
      case a => prankOrMove(a, message(s"$a"))
    }
  }

  //start()

}

object Test extends App {

  val users = Map("Mary" -> 0, "John" -> 3)

  println()
  println(GooseGame.move("Mary", 3, users)._1)
  println()
  println(GooseGame.move("Mary", 3, users)._2)

}