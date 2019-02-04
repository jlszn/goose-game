import GooseGame.Users

object PlayerRegistrationUtil {

  def register: Users = registerUsers(retrieveCount, Map.empty)

  def retrieveCount: Int = {
    println("Enter number of players (from 2 to 6): ")

    val count: String = scala.io.StdIn.readLine()

    if (count.matches("[2-6]"))
      count.toInt
    else {
      println("Wrong input or wrong players count! Try again.")
      retrieveCount
    }
  }

  // add new players
  def registerUsers(usersCount: Int, users: Users): Users =
    if (usersCount == 0) users
    else registerUsers(usersCount - 1, users ++ registerUser(users))

  // receive new player's name
  def registerUser(users: Users): Users = {
    println("Enter player name: ")
    val newUser = scala.io.StdIn.readLine()

    checkUsers(users, newUser)
  }

  // check players' name for uniqueness
  def checkUsers(currentUsers: Users, newUser: String): Users = {

    // if new username is the same as another one in the Map - request a username again
    if (currentUsers.contains(newUser)) {
      println(s"User $newUser already in game!")
      registerUser(currentUsers)
    } else {
      println(s"$newUser joined!")
      Map(newUser -> 0)
    }
  }

}