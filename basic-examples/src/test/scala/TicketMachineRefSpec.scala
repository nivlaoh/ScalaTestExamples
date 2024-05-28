import org.scalatest.refspec.RefSpec

class TicketMachineRefSpec extends RefSpec {
  // Based on reflection, this test will be discovered and executed
  // Note that since Scala.js does not use reflection, this style is for JVM only
  //
  // When to use this?
  // Tests are created as methods in a class compared to other testing styles that
  // create tests as functions, resulting in faster compile times
  // Not a common consideration unless project is large and build time is a concern

  object `Ticket machine` {
    val machine = new TicketMachine
    machine.supplyTickets("ticket1", 10)

    def `allow buying of tickets if enough tickets are available` {
      val result = machine.buyTickets("ticket1", 2)
      assert(result)
    }

    def `not allow buying of tickets if not enough tickets are available` {
      val result = machine.buyTickets("ticket1", 10)
      assert(!result)
    }

    def `throw error if no such ticket is sold` {
      assertThrows[IllegalArgumentException](machine.buyTickets("ticket3", 10))
    }
  }
}