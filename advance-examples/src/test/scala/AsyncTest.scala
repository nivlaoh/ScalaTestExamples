import org.scalatest.{BeforeAndAfterAll, ParallelTestExecution}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

import java.util.UUID.randomUUID
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

class SerialAsyncTest extends AsyncWordSpec with Matchers {
  val service = new TestDbService
  "SerialAsyncTest with serial test execution" should {
    "allow async tests" in {
      val future = service.publish("serial data")
      future.map { result =>
        assert(result.nonEmpty)
      }
    }

    "allow async tests with different data" in {
      val future = service.publish("serial different data")
      future.map { result =>
        result should startWith ("serial different data")
      }
    }
  }
}

class ParallelAsyncTest extends AsyncFlatSpec with ParallelTestExecution with BeforeAndAfterAll with Matchers {
  val service = new TestDbService
  val timer = new Timer

  implicit override def executionContext: ExecutionContext = ExecutionContext
    .fromExecutorService(Executors.newFixedThreadPool(3))

  override def beforeAll(): Unit = super.beforeAll()

  override def afterAll(): Unit = {
    println(s"All tests run in ${timer.tick}ms")
  }

  it should "allow async tests" in {
    val future = service.publish("parallel data")
    future.map { result =>
      assert(result.nonEmpty)
    }
  }

  it should "allow async tests with different data" in {
    val future = service.publish("parallel different data")
    future.map { result =>
      result should startWith ("parallel different data")
    }
  }
}

class TestDbService {
  def publish(data: String)(implicit ec: ExecutionContext): Future[String] = Future {
    println(s"${Thread.currentThread().getName} - Publishing data: $data")
    Thread.sleep(5000)
    s"$data-${randomUUID.toString}"
  }
}

class Timer {
  private var current = System.currentTimeMillis()

  def tick: Long = {
    val snapshot = System.currentTimeMillis()
    val elapsed = snapshot - current
    current = snapshot
    elapsed
  }
}