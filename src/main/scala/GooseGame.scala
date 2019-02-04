object GooseGame extends App {

  type Users = Map[String, Int]

  def initialCommandsProcessing(): Unit = {
    val initialInput = scala.io.StdIn.readLine()

    if (initialInput.equals("about")) {

      RulesOutput.showRules()
      initialCommandsProcessing()

    } else if (initialInput.equals("play")) {

      val users: Map[String, Int] = PlayerRegistrationUtil.register
      println("Push Space bar and Enter to start")

      if (isStarted) {
        println("Let's start!")
        play(users)
      }

    } else {
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

  def start(): Unit = {

    println("Welcome to Goose Game!")
    println("Type 'about' to see the game rules, or 'play' to start users registration.")

    initialCommandsProcessing()
  }

  // returns values from both dice
  def roll(turnOf: String): (Int, Int) = {
    val input = scala.io.StdIn.readLine()

    if (input == s"move $turnOf") {
      val dice = RandomUtil.roll()
      println(s"$turnOf rolled dice: " + dice._1 + ", " + dice._2)
      dice
    } else {
      println("Wrong user")
      roll(turnOf)
    }
  }

  // move to Utils Seq, 63, so on...
  // returns message and new order of users
  def move(user: String, diceSum: Int, users: Users): (String, Users) = {

    // move to rules (?)
    val geese: Seq[Int] = Seq(5, 9, 14, 18, 23, 27)

    // handle emptiness
    // switch to .fold
    val currentPosition: Int = users(user)

    def zero: String = if (currentPosition == 0) "the Start" else s"$currentPosition"

    // messages
    def message(position: String): String =
      s"$user moves from $zero to $position"

    def bouncesMessage(position: Int): String =
      message("63") + s".\n$user bounces! $user returns to ${63 - (position - 63)}"

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
      case a if a > 63 => (bouncesMessage(a), users + (user -> (a - 63)))
      case 63 => (message("63") + s".\n$user Wins!!", users + (user -> 63))
      case 6 => prankOrMove(12, message(s"The Bridge.\n$user jumps to 12."))
      case a if geese.contains(a) && geese.contains(a + diceSum) => prankOrMove(a + diceSum, doubleGoose(a), diceSum)
      case a if geese.contains(a) => prankOrMove(a, gooseMessage(a), diceSum)
      case a => prankOrMove(a, message(s"$a"))
    }
  }

  def play(users: Users): Unit = {

    def playRound(turnOf: String, users: Users): Unit =
      if (users.exists(_._2 == 63)) println("Game over")
      else {
        val dice: (Int, Int) = roll(turnOf)

        def moveThis: (String, Users) = move(turnOf, dice._1 + dice._2, users)

        def movedUsers: Users = {
          val moved = moveThis
          println(moved._1)
          moved._2
        }

        val nextUser: String = CommandUtil.nextUser(users, turnOf)

        playRound(nextUser, movedUsers)
      }

    playRound(RandomUtil.selectFirst(users), users)
  }

  start()

}
