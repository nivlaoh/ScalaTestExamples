import org.scalatest.funspec.AnyFunSpec

class TicketMachineFunSpec extends AnyFunSpec {
  val machine = new TicketMachine
  machine.supplyTickets("ticket1", 10)

  describe("Ticket machine - buying ticket") {
    it("should allow buying of tickets if enough tickets are available") {
      val result = machine.buyTickets("ticket1", 2)
      assert(result)
    }
    it("should not allow buying of tickets if not enough tickets are available") {
      val result = machine.buyTickets("ticket1", 10)
      assert(!result)
    }
    it("should throw error if no such ticket is sold") {
      assertThrows[IllegalArgumentException](machine.buyTickets("ticket3", 10))
    }
  }
}