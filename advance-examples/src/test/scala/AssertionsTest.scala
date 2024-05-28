import org.scalatest.{OptionValues, PartialFunctionValues}
import org.scalatest.wordspec.AnyWordSpec

class AssertionsTest extends AnyWordSpec with OptionValues with PartialFunctionValues {
  // To comment out ignore when testing assertion
  "AssertionsTest" ignore {
    "Raise assertion error when test fails" in {
      val left = 2
      val right = 1
      assert(left == right)
    }

    "Raise assertion error with a clue when test fails" in {
      val left = 2
      val right = 1
      assert(left == right, "Left is not equal to right")
    }

    "Specify expected result when test fails" in {
      val left = 2
      val right = 1
      assertResult(left)(right)
    }

    "Intercept exception when test fails" in {
      val s = "This is a sample string"
      val caught = intercept[IndexOutOfBoundsException] {
        s.charAt(-1)
      }
      assert(caught.getMessage == "String index out of range: -1")
    }

    "throw test failed error when map does not contain a value" in {
      val map = Map("a" -> 1, "b" -> 2)
      // `valueAt` is made available with trait `PartialFunctionValues`
      assert(map.valueAt("c") == 1)
    }

    "throw test failed error when option does not contain a value" in {
      val opt: Option[Int] = None
      // `value` is made available with trait `OptionValues`
      assert(opt.value == 1)
    }
  }
}
