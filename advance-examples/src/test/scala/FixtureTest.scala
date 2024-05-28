import DbServer.{Db, createDb, removeDb}
import org.scalatest.wordspec.AnyWordSpec

import java.util.UUID.randomUUID
import java.util.concurrent.ConcurrentHashMap

class FixtureTest extends AnyWordSpec {
  def withDatabase(testCode: Db => Any): Unit = {
    val dbName = randomUUID.toString
    val db = createDb(dbName) // create the fixture
    try {
      db.append("ScalaTest is ") // setup
      testCode(db) // Provide fixture to test
    }
    finally removeDb(dbName) // clean up the fixture
  }

  "FixtureTest" should {
    "enable common repeatable logic to be wired easily" in withDatabase { db =>
      db.append("fun to use!")
      assert(db.toString == "ScalaTest is fun to use!")
    }
  }
}

object DbServer {
  type Db = StringBuffer

  private val databases = new ConcurrentHashMap[String, Db]

  def createDb(name: String): Db = {
    val db = new StringBuffer
    databases.put(name, db)
    db
  }

  def removeDb(name: String): Db = {
    databases.remove(name)
  }
}