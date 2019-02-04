import GooseGame.Users

object PlayerRegistrationUtil {

  // returns registered users in the end, until then side-effects with printlns
  def register: Users = {
    regUsers(retrieveCount, Map())
  }

  def retrieveCount: Int = {
    println("Enter number of players: ")

    val count = scala.io.StdIn.readLine()
    if (count.matches("[2-9]*")) {
      count.toInt
    } else {
      println("Wrong input! Try again.")
      retrieveCount
    }
  }

  //add new players
  def regUsers(usersCount: Int, users: Users): Users = {
    if (usersCount == 0) {
      users
    } else {
      regUsers(usersCount - 1, users ++ registerUser(users))
    }
  }

  //retrieve name for new player
  def registerUser(users: Users): Users = {
    println("Enter player name: ")
    val newUser = scala.io.StdIn.readLine()

    checkUsers(users, newUser)
  }

  //check player names for unique and if the identical - retrieve new username for Player
  def checkUsers(currentUsers: Users, newUser: String): Users = {

    //if newName equals to some player in Map - requesting username again
    if (currentUsers.contains(newUser)) {
      println(s"User $newUser already in game!")
      registerUser(currentUsers)
    } else {
      println(s"$newUser joined!")
      Map(newUser -> 0)
    }
  }
}
