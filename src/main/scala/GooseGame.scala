
object GooseGame extends App {

  // (un)expected behaviour to handle:

  // check if name doesn't yet exist
  // after two users are registered do not accept any more of them. If input is any other than Space, give user a hint
  // check if moving input is correct, else give a hint: "To move a player type "Move name""
  // check if "move $name" is a registered name
  // check if it's the turn of the user from the input, else give user a hint

  // additional features if we still have time tomorrow
  // provide a way to finish the game anytime - ask if the user wants to restart or just quit the app
  // provide a game description/rules on "about" input
  def start() = {

    val users = PlayerRegistrationUtil.register

    println("Push Space bar and Enter to start")
    if (isStarted) {
      println("Start")
      //      play(users)
    }

    // get input until two users are registered

    // on Space - output "Start" and call play()

  }

  def isStarted: Boolean = {
    if (scala.io.StdIn.readLine() != " ") {
      println("Hint: for start a game - press Space bar and Enter")
      isStarted
    } else {
      true
    }
  }

  // Вадим
  // returns values from both dice
  def roll: (Int, Int) = ???

  def play(users: (String, String), positions: (Int, Int) = (0, 0)): Unit = {

    val user1: String = users._1
    val user2: String = users._2

    def playRound(turnOf: String, position1: Int = 0, position2: Int = 0): Unit =
      if (position1 != 63 && position2 != 63) {

        // get input
        // validate user: name exists, their turn

        val dice: (Int, Int) = roll

        // output "$user rolls $dice_.1, $dice._2"

        val moveThis = (oldPosition: Int) => move(dice._1 + dice._2, oldPosition)

        val newPosition: Int => Int = (oldPosition: Int) => moveThis(oldPosition)._2
        // val moveMessage = (oldPosition: Int) => moveThis(oldPosition)._2

        val isTurnOfUser1 = turnOf == user1

        val nextUser: String = if (isTurnOfUser1) user2 else user1
        val updatePosition: Int = if (isTurnOfUser1) newPosition(position1) else newPosition(position2)

        if (isTurnOfUser1) playRound(nextUser, updatePosition, position2) else playRound(nextUser, position1, updatePosition)
      } else println("Game over")

    //play();
    playRound(user1)
  }

  // returns new position and message
  // $user moves from $currentPosition to $newPosition"
  // The Goose message
  // The Bridge message
  // etc
  def move(diceSum: Int, currentPosition: Int): (String, Int) = ???


  start()

}