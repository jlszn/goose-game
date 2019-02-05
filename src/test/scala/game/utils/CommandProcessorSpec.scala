package game.utils

import org.specs2.mutable.Specification

class CommandProcessorSpec extends Specification {

  val testMap = Map("A" -> 1, "B" -> 2, "C" -> 3, "D" -> 4)

  "This is a specification for CommandProcessor" >> {

    "nextUser() method should" >> {
      "return next user" >> {
        CommandProcessor.nextUser(testMap, "A") must_== "B"
      }

      "return the first when current is the last" >> {
        CommandProcessor.nextUser(testMap, "D") must_== "A"
      }
    }

  }

}
