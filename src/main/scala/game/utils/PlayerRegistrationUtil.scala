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

    val count: String = InputMatcher.getInput // get count of players from console

    InputMatcher.getType(count) match { // check what input from console is Int
      case RightPlayersNumber => count.toInt // if Int - return number for players
      case _ =>
        println("Wrong input or wrong players count! Try again.") // if not - sent error message to console and call retrieveCount method again for input count of players
        retrieveCount
    }
  }

  /**
   * Method for registering new players in game
   *
   * @param usersCount Count of Players
   * @param users      already registered Players
   * @return Map of registered users
   */
  def registerUsers(usersCount: Int, users: Users): Users =
    if (usersCount == 0) users //if count of users end - return already registered players
    else registerUsers(usersCount - 1, users ++ registerUser(users)) // if count still not 0 - register new player recursively

  /**
   * Method for receiving new username from console and passing it to be checked for uniqueness
   *
   * @param users already registered Players
   * @return Map - of registered users
   */
  def registerUser(users: Users): Users = {
    println("Enter player name: ")
    val newUser = InputMatcher.getInput //retrieve username form console

    if (newUser.length == 0 || newUser == " ") {
      println("Player name can't be empty")
      registerUser(users)
    } else {
      checkUsers(users, newUser) // pass new username to check for uniqueness
    }
  }

  /**
   * Method for checking players' names for uniqueness
   *
   * @param newUser      new player name for checking
   * @param currentUsers already registered Players
   * @return Map of registered users
   */
  def checkUsers(currentUsers: Users, newUser: String): Users = {

    // if new username is the same as another one in the Map - request a username again
    if (currentUsers.contains(newUser)) {
      println(s"User $newUser already in game!")
      registerUser(currentUsers) //if user already exists in map - call registerUser again for retrieve new username/
    } else {
      println(s"$newUser joined!")
      Map(newUser -> 0)//if username are unique - put it into map with zero position
    }
  }

}