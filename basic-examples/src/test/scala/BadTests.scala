import org.scalatest.wordspec.AnyWordSpec

class BadTests extends AnyWordSpec {
  // 1. Description of this test does not describe the component to be tested
  "test1" should {
    // 2. Test name does not indicate the passing criteria
    "pass" in {
      val countries = Seq("Canada", "Australia", "Japan", "SpAiN")
      val sorter = new Sorter
      val modifier = new CaseModifier
      val result = sorter.sort(modifier.toUpper(countries))
      assert(result == Seq("AUSTRALIA", "CANADA", "JAPAN", "SPAIN"))
      // 3. Are we testing uppercase functionality or sequence sorting?
    }
  }
}

class CaseModifier {
  def toUpper(list: Seq[String]): Seq[String] = list.map(_.toUpperCase)
}

class Sorter {
  def sort(list: Seq[String]): Seq[String] = list.sorted
}
