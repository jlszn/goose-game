import java.security.SecureRandom

object RandomUtil {

  private val START = 1
  private val END = 6

  private val random = new SecureRandom()

  def roll(): (Int, Int) = (random.nextInt(END) + START, random.nextInt(END) + START)

  def selectFirst(users: Map[String, Int]): String = users.keys.toList.apply(random.nextInt(users.size))

}
