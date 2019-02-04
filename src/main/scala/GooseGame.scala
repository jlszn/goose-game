object GooseGame /* extends App*/ {

  private val END = 63

  private val GOOSE_SEQ = Seq(5, 9, 14, 18, 23, 27)

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
//  def register: (String, String) = ("", "")

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

            def move(turnOf: String, users: Map[String, Int] = users, message: String = ""): (String, Map[String, Int]) ={
              val moveStr = (newPosition: Int) =>
                if (message.isEmpty) s"$turnOf rolls ${dices._1}, ${dices._2}. $turnOf moves from ${users(turnOf)} to $newPosition."
                else message
              users(turnOf) + dices._1 + dices._2 match {
                case END => (s"${moveStr(END)} $turnOf Wins!!", Map(turnOf -> END))
                case newPosition if newPosition > 63 => (s"${moveStr(END)} $turnOf bounces! $turnOf returns to ${2 * 63 - newPosition}", Map(turnOf -> (2 * 63 - newPosition)))
                case newPosition if GOOSE_SEQ.contains(newPosition) => move(turnOf, users + (turnOf -> newPosition), moveStr(newPosition) + s" The Goose. $turnOf moves again ang goes to ${newPosition + dices._1 + dices._2}.")
                case newPosition => (s"${moveStr(newPosition)}", Map(turnOf -> newPosition))
              }
            }

            val moved = move(turnOf)

            println(moved._1)

            playRound(users ++ moved._2 , users.keysIterator.toList((users.keysIterator.indexOf(turnOf) + 1) % users.size))

          } else println("Game Over")

    playRound(users, users.head._1)

  }
}

object Test extends App {

  println(GooseGame.play(Map("John" -> 0, "Mary" -> 0 )))

}