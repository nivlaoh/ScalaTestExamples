import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.scalatest.concurrent.Eventually.eventually
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.selenium.WebBrowser

class SeleniumTest extends AnyWordSpec with WebBrowser {
  implicit val webDriver: WebDriver = new HtmlUnitDriver

  "Google home page" should {
    "have the correct title" in {
      go to "https://www.google.com"
      assert(pageTitle == "Google")
    }

    "be able to display search results" in {
      go to "https://www.google.com"
      textArea("q").value = "selenium"
      submit()
      eventually {
        assert(pageTitle.contains("selenium - Google Search"))
      }
    }
  }
}
