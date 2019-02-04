object GooseGame /* extends App*/ {

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

    play(users)
  }

  // Вадим
  // returns values from both dice
  def roll(turnOf: String, users: (String, String)): (Int, Int) = {
    val input = scala.io.StdIn.readLine()

    if (input == s"$turnOf roll") (6, 6) else {
      println("Wrong directive")
      roll(turnOf, users)
    }
  }

  def play(users: (String, String), positions: (Int, Int) = (0, 0)): Unit = {

    // represent 0 with "Start"

    val user1: String = users._1
    val user2: String = users._2

    def playRound(turnOf: String, position1: Int = 0, position2: Int = 0): Unit =
      if (position1 != 63 && position2 != 63) {

        val dice: (Int, Int) = roll(turnOf, users)

        def moveThis(oldPosition: Int): (String, Int) = move(turnOf, dice._1 + dice._2, oldPosition)

        def newPosition(oldPosition: Int): Int = {
          val moved = moveThis(oldPosition)
          println(moved._1)
          moved._2
        }

        val isTurnOfUser1 = turnOf == user1

        val nextUser: String = if (isTurnOfUser1) user2 else user1
        val updatePosition: Int = if (isTurnOfUser1) newPosition(position1) else newPosition(position2)

        if (isTurnOfUser1) playRound(nextUser, updatePosition, position2)
        else playRound(nextUser, position1, updatePosition)

      }
      else println("Game over")

    playRound(user1)
  }

  // returns new position and message
  // $user moves from $currentPosition to $newPosition"
  // The Goose message
  // The Bridge message
  // etc

  val geese: Seq[Int] = Seq(5, 9, 14, 18, 23, 27)

  // add bouncing
  def move(user: String, diceSum: Int, currentPosition: Int): (String, Int) = {


    def message(position: String): String =
      s"$user moves from ${if (currentPosition == 0) "Start" else currentPosition} to $position"

    def bouncesMessage(position: Int): String =
      message("63") + s". $user bounces! $user returns to ${63 - (position - 63)}"

    def gooseMessage(position: Int): String =
      message(s"$position, The Goose. $user moves again and goes to ${position + diceSum}")

    def doubleGoose(position: Int): String =
      gooseMessage(position) + s", The Goose. $user moves again and goes to ${position + diceSum + diceSum}."

    diceSum + currentPosition match {
      case a if a > 63 => (bouncesMessage(a), a - 63)
      case 63 => (message("63") + s"$user Wins!!", 63)
      case 6 => (message(s"The Bridge. $user jumps to 12"), 12)
      case a if geese.contains(a) && geese.contains(a + diceSum) => (doubleGoose(a), a + diceSum)
      case a if geese.contains(a) => (gooseMessage(a + diceSum), a + diceSum)
      case a => (message(s"$a"), a)
    }
  }

  //start()

}

object Test extends App {

  //  println(GooseGame.play(("John", "Mary")))
  println(GooseGame.move("Mary", 5, 62)._1)

}