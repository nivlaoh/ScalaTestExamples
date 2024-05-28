import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable

class BehaviourTest extends AnyWordSpec {
  "BehaviourTest" should {
    val machine = new CoffeeMachine

    "test the outcome of making coffee" in {
      val coffee = machine.makeHotCoffee()
      assert(coffee == Seq("Hot water added", "Coffee powder added", "Milk added", "Stirred coffee"))
    }

    // Not a good test if internal implementation is not a part of requirements
    "not test specifically if other internal methods are called" in {
      val coffee = machine.makeHotCoffee()
      assert(machine.hotWaterDispenseCount.get() == 2)
      assert(machine.coffeePowderDispenseCount.get() == 2)
      assert(machine.milkDispenseCount.get() == 2)
      assert(coffee == Seq("Hot water added", "Coffee powder added", "Milk added", "Stirred coffee"))
    }
  }
}

class CoffeeMachine {
  private val steps: mutable.Buffer[String] = mutable.Buffer.empty
  val hotWaterDispenseCount = new AtomicInteger(0)
  val coffeePowderDispenseCount = new AtomicInteger(0)
  val milkDispenseCount = new AtomicInteger(0)

  private def addHotWater(): Unit = {
    steps += "Hot water added"
    hotWaterDispenseCount.incrementAndGet()
  }

  private def addCoffeePowder(): Unit = {
    steps += "Coffee powder added"
    coffeePowderDispenseCount.incrementAndGet()
  }

  private def addMilk(): Unit = {
    steps += "Milk added"
    milkDispenseCount.incrementAndGet()
  }

  private def stirCoffee(): Unit = {
    steps += "Stirred coffee"
  }

  def makeHotCoffee(): Seq[String] = {
    require(steps.isEmpty, "Coffee machine is in use. Please wait for the current coffee to be prepared.")
    addHotWater()
    addCoffeePowder()
    addMilk()
    stirCoffee()
    val result = steps.toList
    steps.clear()
    result
  }
}
