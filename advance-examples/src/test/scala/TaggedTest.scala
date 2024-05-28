import org.scalatest.Tag
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

object ExampleTag extends Tag("TaggedTest")

class TaggedTest extends AnyWordSpec with Matchers {
  "TaggedTest" should {

    // can be tested in sbt shell with the following command (Assuming at root project)
    // advanceExamples/testOnly -- -n TaggedTest
    "allow tagged tests" taggedAs ExampleTag in {
      "This test is tagged with ExampleTag" should include("ExampleTag")
    }
  }
}
