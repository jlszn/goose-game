object CommandUtil {

  val about =
    """
      |It's an Scala implementation of "The Goose Game Kata" (https://en.wikipedia.org/wiki/Game_of_the_Goose)
      |How to use:
      | 1. Define an amount of users and write it in a console.
      | 2. Write users' names in the console
      | 3. Write "move <some-player-name>"
      | 4. Repeat until win
      |Rules:
      | During the game program will throw 2 dices every turn. Values of such dices define your future steps.
      | There are some zone
    """.stripMargin

  def nextUser(users: Map[String, Int], current: String): String = users.keysIterator.toList(users.keysIterator.indexOf(current) + 1 % users.size)

}
