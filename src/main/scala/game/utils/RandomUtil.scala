package game.utils

import java.security.SecureRandom

import game.Users

/**
  * RandomUtil is a util class that contains methods for controlling random processes inside of the application.
  */
object RandomUtil {
  /**
    * The least cube side
    */
  private val START = 1
  /**
    * The biggest cube side
    */
  private val END = 6

  /**
    * Pseudo-number generator. A resulting sequence of SecureRandom is less predictable than sequence of Random.
    * SecureRandom is initialised without any seed, so it will use it's own.
    */
  private val random = new SecureRandom()

  /**
   * This method is used for generating a pair of dice rolls. It uses SecureRandom so a result can be hardly predicted
   *
   * @return two dice' values
   */
  def roll(): (Int, Int) = (random.nextInt(END) + START, random.nextInt(END) + START)

  /**
   * This method selects the first user for the game.
   *
   * @param users current users
   * @return the first user
   */
  def selectFirst(users: Users): String = {
    println(s"Now we will see how will be the first to move!")

    val first = if (users.nonEmpty) users.keys.toList.apply(random.nextInt(users.size)) else ""

    if (first.isEmpty) println("You have no users")

    first
  }

}
