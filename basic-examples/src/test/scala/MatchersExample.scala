import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.matchers.should._
import org.scalatest.wordspec.AnyWordSpec

import java.io.File
import java.nio.file.Paths

class MatchersExample extends AnyWordSpec with Matchers {
  "MatchersExample" should {
    val reader = new TestReader

    "express equality" in {
      val result = Array(1, 2) == Array(1, 2)
      result should be(false)
      Array(1, 2) should equal (Array(1, 2))
    }

    "express assertion through boolean properties" in {
      val f = reader.getFile("build.sbt")
      f should be a 'file
      f should not be 'directory
    }

    "express assertion on list elements" in {
      val list = Seq(1, 4, 6, 5)

      Some(6) should contain oneOf (5, 6, 7)
      list should contain (4)
    }

    "express assertion on map elements" in {
      val map = Map("Alice" -> 1, "Alan" -> 4, "Brian" -> 2, "Janice" -> 10)

      map should contain key "Alice"
      map should contain ("Alan" -> 4)
    }

    "enable assertion through custom matchers" in {
      import CustomMatchers._

      reader.getFile("build.sbt") should endWithExtension (".sbt")
    }
  }
}

class TestReader {
  def getFile(path: String): File = {
    Paths.get(path).toFile
  }
}

trait CustomMatchers {
  class FileEndsWithExtensionMatcher(expectedExtension: String) extends Matcher[File] {
    def apply(left: File): MatchResult = {
      val name = left.getName
      MatchResult(
        name.endsWith(expectedExtension),
        s"""File $name did not end with extension "$expectedExtension"""",
        s"""File $name ended with extension "$expectedExtension""""
      )
    }
  }

  def endWithExtension(expectedExtension: String) = new FileEndsWithExtensionMatcher(expectedExtension)
}

object CustomMatchers extends CustomMatchers