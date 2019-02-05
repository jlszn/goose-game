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

    val count: String = InputMatcher.getInput

    InputMatcher.getType(count) match {
      case RightPlayersNumber => count.toInt
      case _ =>
        println("Wrong input or wrong players count! Try again.")
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
  // if count of users end - return already registered players
    if (usersCount == 0) users
    // if count still not 0 - register new player recursively
    else registerUsers(usersCount - 1, users ++ registerUser(users))

  /**
   * Method for receiving new username from console and passing it to be checked for uniqueness
   *
   * @param users already registered Players
   * @return Map - of registered users
   */
  def registerUser(users: Users): Users = {
    println("Enter player name: ")

    //retrieve username form console
    val newUser = InputMatcher.getInput

    if (newUser.length == 0 || newUser.trim.isEmpty) {
      println("Player name can't be empty")
      registerUser(users)
    } else {
      // pass new username to check for uniqueness
      checkUsers(users, newUser)
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
      println(s"User $newUser already in the game!")
      // if user already exists in map - call registerUser again for retrieve new username
      registerUser(currentUsers)
    } else {
      println(s"$newUser joined!")
      // if username is unique - put it into map with zero position
      Map(newUser -> 0)
    }
  }

}