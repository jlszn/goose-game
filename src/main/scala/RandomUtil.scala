import java.security.SecureRandom

import GooseGame.Users

object RandomUtil {

  private val START = 1
  private val END = 6

  private val random = new SecureRandom()

  def roll(): (Int, Int) = (random.nextInt(END) + START, random.nextInt(END) + START)

  def selectFirst(users: Users): String = {
    println(s"Now we will see how will be the first to move!")
    val first = users.keys.toList.apply(random.nextInt(users.size))
    println(s"$first make your move!")
    first
  }

}
