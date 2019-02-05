package game.utils

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import Rules.START_HINT

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

    "isStarted() should" >> {

      "accept correct input" >> {
        val input = new ByteArrayInputStream(s" ${System.lineSeparator()}".getBytes())
        Console.withIn(input) {
          CommandProcessor.isStarted must_== true
        }
      }

      "try again if input is wrong" >> {
        val input = new ByteArrayInputStream(("Wrong" + s"${System.lineSeparator()}" + " " + s"${System.lineSeparator()}").getBytes())
        Console.withIn(input) {
          CommandProcessor.isStarted must_== true
        }
      }

      "give a hint if input is wrong" >> {
        val input = new ByteArrayInputStream(("Wrong" + s"${System.lineSeparator()}" + " " + s"${System.lineSeparator()}").getBytes())
        val output = new ByteArrayOutputStream()
        Console.withOut(output) {
          Console.withIn(input) {
            CommandProcessor.isStarted
          }
        }
        output.toString must beEqualTo(START_HINT + "\n")
      }

    }

  }

}
