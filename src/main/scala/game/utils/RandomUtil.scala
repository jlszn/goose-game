package game.utils

import java.security.SecureRandom

import game.Users

object RandomUtil {
  private val START = 1
  private val END = 6

  private val random = new SecureRandom()

  /**
   * This method is used for generating a pair of dice rolls. It uses SecureRandom so a result can be hardly predicted
   *
   * @return
   */
  def roll(): (Int, Int) = (random.nextInt(END) + START, random.nextInt(END) + START)

  /**
   * This method selects the first user for the game.
   *
   * @param users current users
   * @return the first User
   */
  def selectFirst(users: Users): String = {
    println(s"Now we will see how will be the first to move!")

    val first = users.keys.toList.apply(random.nextInt(users.size))

    println(s"$first make your move!")

    first
  }

}
