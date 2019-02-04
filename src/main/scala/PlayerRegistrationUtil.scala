object PlayerRegistrationUtil {

  // returns two registered users in the end, until then side-effects with printlns
  def register: (String, String) = {
    println("Enter first player name:")
    val user1 = scala.io.StdIn.readLine()

    checkUsers(user1, registerSecondUser)
  }

  //retrieve second username
  def registerSecondUser: String = {
    println("Enter second player name:")
    scala.io.StdIn.readLine()
  }

  //check player names for unique and if the identical - retrieve new username for second Player
  def checkUsers(user1: String, user2: String): (String, String) = {
    if (user2.equals(user1)) {
      println(s"User $user2 already in game")
      checkUsers(user1, registerSecondUser)
    } else {
      (user1, user2)
    }
  }
}
