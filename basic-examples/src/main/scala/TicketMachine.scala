import java.util.concurrent.atomic.AtomicInteger
import scala.collection.convert.Wrappers.JConcurrentMapWrapper

import java.util.{concurrent => juc}

class TicketMachine {
  private val ticketMap: collection.concurrent.Map[String, AtomicInteger] = JConcurrentMapWrapper(new juc.ConcurrentHashMap[String, AtomicInteger]())

  def hasTicket(ticketName: String): Boolean = ticketMap.contains(ticketName)
  def buyTickets(ticketName: String, number: Int): Boolean = {
    require(ticketName != null && ticketName.nonEmpty, "Ticket name must not be null or empty")
    require(number > 0, "Number of tickets to buy must be positive")
    ticketMap
      .get(ticketName)
      .map(counter => {
        if (counter.get() >= number) {
          counter.addAndGet(-number)
          true
        } else {
          false
        }
      })
      .getOrElse(throw new IllegalArgumentException(s"Ticket $ticketName not found"))
  }

  def supplyTickets(ticketName: String, number: Int): Int = {
    require(ticketName != null && ticketName.nonEmpty, "Ticket name must not be null or empty")
    require(number > 0, "Number of tickets to supply must be positive")

    ticketMap
      .getOrElseUpdate(ticketName, new AtomicInteger())
      .addAndGet(number)
  }
}