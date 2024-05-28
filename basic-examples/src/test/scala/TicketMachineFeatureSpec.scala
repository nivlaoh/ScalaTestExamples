import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec

class TicketMachineFeatureSpec extends AnyFeatureSpec with GivenWhenThen {
  Feature("Ticket machine") {
    info("As a customer")
    info("I want to be able to buy tickets")
    info("So that I can attend the event")

    Scenario("allow buying of tickets if enough tickets are available") {
      Given("I have 10 tickets for ticket1")
      val machine = new TicketMachine
      machine.supplyTickets("ticket1", 10)

      When("I buy 2 tickets of ticket1")
      val result = machine.buyTickets("ticket1", 2)

      Then("It should be successful")
      assert(result)
    }

    Scenario("not allow buying of tickets if not enough tickets are available") {
      Given("I have 10 tickets for ticket1")
      val machine = new TicketMachine
      machine.supplyTickets("ticket1", 10)

      When("I buy 100 tickets of ticket1")
      val result = machine.buyTickets("ticket1", 100)

      Then("It should not be successful")
      assert(!result)
    }

    Scenario("throw error if no such ticket is sold") {
      Given("I have 10 tickets for ticket1")
      val machine = new TicketMachine
      machine.supplyTickets("ticket1", 10)

      When("I buy 10 tickets of ticket3")
      Then("It should throw an error")
      assertThrows[IllegalArgumentException](machine.buyTickets("ticket3", 10))
    }

    // When implementation of the test is not yet ready, you can mark it as pending
    Scenario("allow supplying of tickets")(pending)
  }
}