import org.scalacheck.Gen
import org.scalatest.freespec.AnyFreeSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalatest.propspec.AnyPropSpec
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable

class TicketMachineWordSpec extends AnyWordSpec {
  val machine = new TicketMachine
  machine.supplyTickets("ticket1", 10)

  "Ticket machine" should {
    "allow buying of tickets if enough tickets are available" in {
      val result = machine.buyTickets("ticket1", 2)
      assert(result)
    }
    "not allow buying of tickets if not enough tickets are available" in {
      val result = machine.buyTickets("ticket1", 10)
      assert(!result)
    }
    "throw error if no such ticket is sold" in {
      assertThrows[IllegalArgumentException](machine.buyTickets("ticket3", 10))
    }
  }
}

class TicketMachineFreeSpec extends AnyFreeSpec {
  val machine = new TicketMachine
  machine.supplyTickets("ticket1", 10)

  "Ticket machine" - {
    "allow buying of tickets if enough tickets are available" in {
      val result = machine.buyTickets("ticket1", 2)
      assert(result)
    }
    "not allow buying of tickets if not enough tickets are available" in {
      val result = machine.buyTickets("ticket1", 10)
      assert(!result)
    }
    "throw error if no such ticket is sold" in {
      assertThrows[IllegalArgumentException](machine.buyTickets("ticket3", 10))
    }
  }
}

class TicketMachinePropSpec extends AnyPropSpec with ScalaCheckPropertyChecks {
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 12, sizeRange = 50)
  val validConcertNamesGen: Gen[String] = for (num <- Gen.choose(0, 10)) yield s"Concert$num"
  val validTicketSupplyGen: Gen[Int] = for (num <- Gen.choose(1, 2000)) yield num
  val machine = new TicketMachine

  property("Ticket machine should allow buying of tickets if enough tickets are available") {
    forAll(validConcertNamesGen, validTicketSupplyGen) { (ticket: String, number: Int) =>
      machine.supplyTickets(ticket, number)
      println(s"Buying ${number/2} [$ticket] tickets...")
      assert(machine.buyTickets(ticket, number / 2))
    }
  }

  property("Ticket machine should not allow buying of tickets if not enough tickets are available") {
    machine.supplyTickets("SpecialConcert5", 1)
    val result = machine.buyTickets("SpecialConcert5", 10)
    assert(!result)
  }

  property("Ticket machine should throw error if no such ticket is sold") {
    assertThrows[IllegalArgumentException](machine.buyTickets("SpecialConcert3", 10))
  }
}