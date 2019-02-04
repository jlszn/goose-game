object GooseGame /* extends App*/ {

  private val END = 63

  // (un)expected behaviour to handle:

  // check if name doesn't yet exist
  // after two users are registered do not accept any more of them. If input is any other than Space, give user a hint
  // check if moving input is correct, else give a hint: "To move a player type "Move name""
  // check if "move $name" is a registered name
  // check if it's the turn of the user from the input, else give user a hint

  // additional features if we still have time tomorrow:

  // provide a way to finish the game anytime - ask if the user wants to restart or just quit the app
  // provide a game description/rules on "about" input
  // provide a way to change names
  // tests
  // randomize who would be the first to roll

  // validate with Eithers?

  // Игорь
  // returns two registered users in the end, until then side-effects with printlns
  def register: (String, String) = ("", "")

//  def start(): Unit = {
//
//    println("Welcome to Goose Game!")
//
//    val users: (String, String) = register
//
//    println("Let's start!")
//
//    play(users)
//  }

  // Вадим
  // returns values from both dice
  def roll(turnOf: String): (Int, Int) = {
    val input = scala.io.StdIn.readLine()

    if (input == s"move $turnOf") RandomUtil.roll() else {
      println ("Wrong user")
      roll(turnOf)
    }
  }

  def play(users: Map[String, Int]): Unit = {

        def playRound(users: Map[String, Int], turnOf: String): Unit =
          if (!users.valuesIterator.contains(END)) {

            val dices = roll(turnOf)

            def move(turnOf: String, users: Map[String, Int]): (String, Map[String, Int]) ={
              val moveStr = (newPosition: Int) => s"$turnOf rolls ${dices._1}, ${dices._2}. $turnOf moves to $newPosition."
              users(turnOf) + dices._1 + dices._2 match {
                case END => (s"${moveStr(END)} $turnOf Wins!!", Map(turnOf -> END))
                case newPosition if newPosition > 63 => (s"${moveStr(END)} $turnOf bounces! $turnOf returns to ${2 * 63 - newPosition}", Map(turnOf -> (2 * 63 - newPosition)))
                case newPosition => (s"${moveStr(newPosition)}", Map(turnOf -> newPosition))
              }
            }

            val moved = move(turnOf, users)

            println(moved._1)

            playRound(users ++ moved._2 , users.keysIterator.toList((users.keysIterator.indexOf(turnOf) + 1) % users.size))

          } else println("Game Over")
//
//    // represent 0 with "Start"
//
//    def playRound(turnOf: String, position1: Int = 0, position2: Int = 0): Unit =
//      if (position1 != 63 && position2 != 63) {
//
//        val dice: (Int, Int) = roll(turnOf)
//
//        def moveThis(oldPosition: Int): (String, Int) = move(turnOf, (dice._1, dice._2), oldPosition)
//
//        def newPosition(oldPosition: Int): Int = {
//          val moved = moveThis(oldPosition)
//          println(moved._1)
//          moved._2
//        }
//
//        val isTurnOfUser1 = turnOf == user1
//
//        val nextUser: String = if (isTurnOfUser1) user2 else user1
//        val updatePosition: Int = if (isTurnOfUser1) newPosition(position1) else newPosition(position2)
//
//        if (isTurnOfUser1) playRound(nextUser, updatePosition, position2)
//        else playRound(nextUser, position1, updatePosition)
//
//      }
//      else println("Game over")
//
    playRound(users, users.head._1)
  }

  // returns new position and message
  // $user moves from $currentPosition to $newPosition"
  // The Goose message
  // The Bridge message
  // etc

  // here 0 as Start
//  def move(user: (String, Int), dices: (Int, Int)): (String, Int) ={
//    val rollStr = (newPosition: Int) =>  s"${user._1} rolls ${dices._1}, ${dices._2}. ${user._1} moves from ${user._2} to $newPosition."
//    dices._1 + dices._2 + user._2 match {
//      case a if a == 63 => (s"${rollStr(63)} ${user._1} Wins!", 63)
//      case a if a > 63 => (s"${rollStr(63)} ${user._1} bounces! ${user._1} returns to ${2 * 63 - a}", 2 * 63 - a)
//      case newPosition => (s"${rollStr(newPosition)}", newPosition)
//    }
//  }

  //start()



}

object Test extends App {

  println(GooseGame.play(Map("John" -> 0, "Mary" -> 0 )))

}