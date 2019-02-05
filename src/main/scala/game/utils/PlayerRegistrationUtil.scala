package game.utils

import game.Users

 /**
  * PlayerRegistrationUtil is a util class that contains methods for registration logic.
  */
object PlayerRegistrationUtil {

  def register: Users = registerUsers(retrieveCount, Map.empty)

  /**
   * Method for retrieving count of players from console
   *
   * @return Int - number of players
   */
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

  /**
   * Method for registering new players in game
   *
   * @param usersCount Count of Players
   * @param users already registered Players
   * @return Map of registered users
   */
  def registerUsers(usersCount: Int, users: Users): Users =
    if (usersCount == 0) users
    else registerUsers(usersCount - 1, users ++ registerUser(users))

  /**
   * Method for receiving new username from console and passing it to be checked for uniqueness
   *
   * @param users already registered Players
   * @return Map - of registered users
   */
  def registerUser(users: Users): Users = {
    println("Enter player name: ")
    val newUser = scala.io.StdIn.readLine()

    checkUsers(users, newUser)
  }

  /**
   * Method for checking players' names for uniqueness
   *
   * @param newUser new player name for checking
   * @param currentUsers already registered Players
   * @return Map of registered users
   */
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