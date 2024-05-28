import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.atomic.AtomicInteger

class BeforeAfterTest extends AnyWordSpec with BeforeAndAfterAll with BeforeAndAfterEach {
  val service = new TestSharedService

  override def beforeAll(): Unit = {
    println("Before all tests")
    service.setValue(100)
  }

  override def afterAll(): Unit = {
    println("After all tests")
  }

  override def beforeEach(): Unit = {
    println("Before each test")
  }

  override def afterEach(): Unit = {
    println("After each test")
    service.setValue(1)
  }

  "BeforeAfterTest" should {
    "print number equality" in {
      assert(service.getCount == 100)
      service.invokeOp
      assert(service.getCount == 101)
      service.setValue(20)
    }

    "print string equality" in {
      assert(s"ScalaTest has value ${service.getCount}" == "ScalaTest has value 1")
    }
  }
}

class TestSharedService {
  private val counter = new AtomicInteger(0)

  def invokeOp: Int = counter.incrementAndGet()

  def getCount: Int = counter.get()

  def setValue(value: Int): Unit = counter.set(value)
}