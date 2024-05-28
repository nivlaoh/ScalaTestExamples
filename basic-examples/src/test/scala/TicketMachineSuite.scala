import org.scalatest.funsuite.AnyFunSuite

class TicketMachineSuite extends AnyFunSuite {
  val machine = new TicketMachine
  machine.supplyTickets("ticket1", 10)

  test("Ticket machine should allow buying of tickets if enough tickets are available") {
    val result = machine.buyTickets("ticket1", 2)
    assert(result)
  }

  test("Ticket machine should not allow buying of tickets if not enough tickets are available") {
    val result = machine.buyTickets("ticket1", 10)
    assert(!result)
  }

  test("Ticket machine should throw error if no such ticket is sold") {
    assertThrows[IllegalArgumentException](machine.buyTickets("ticket3", 10))
  }

}

