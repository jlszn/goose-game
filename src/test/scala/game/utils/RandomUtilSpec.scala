package game.utils

import org.specs2.mutable.Specification

class RandomUtilSpec extends Specification {

  "This ia a specification for RandomUtil" >> {

    "roll() method should return a tuple of positive numbers between 1 and 6" >> {
      val roll = RandomUtil.roll()
      roll._2 must beBetween(1, 6) and(roll._1 must beBetween(1, 6))
    }

    "selectFirst() method should select a user from the collection" >> {
      val testMap = Map("A" -> 1, "B" -> 2, "C" -> 3, "D" -> 4)
      testMap.keysIterator.toList must contain(RandomUtil.selectFirst(testMap))
    }

    "selectFirst() method should return an empty string if the collection is empty" >> {
      RandomUtil.selectFirst(Map()).must_==("")
    }

  }

}
