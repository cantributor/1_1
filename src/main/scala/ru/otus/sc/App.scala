package ru.otus.sc

import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl

trait App {

  /**
    * Greet requested person
    * @param request - greet request
    * @return the greeting response
    */
  def greet(request: GreetRequest): GreetResponse

  /**
    * Call counter
    * @return number of calls
    */
  def count: Int

  /**
    * Echo of request
    * @param request - some request
    * @return request
    */
  def echo(request: GreetRequest): GreetRequest

  /**
    * Return value depending on key (1, 2 or 3 only)
    * @param key - one of (1, 2, 3)
    * @return Some(value) if key in (1, 2, 3) None otherwise
    */
  def getValue(key: Int): Option[String]

  /**
    * Set value for key
    * @param key - one of (1, 2, 3)
    * @param value - value for key
    * @return true if value set false if key does not exist
    */
  def setValue(key: Int, value: String): Boolean

  /**
    * Get lazy evaluated value
    * @return some value
    */
  def getLazyVal: String
}

object App {
  private class AppImpl(greeting: GreetingService) extends App {
    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    def count: Int                                  = greeting.count
    def echo(request: GreetRequest): GreetRequest   = request
    def getValue(key: Int): Option[String]          = greeting.getValue(key)
    def setValue(key: Int, value: String): Boolean  = greeting.setValue(key, value)
    def getLazyVal: String                          = greeting.getLazyVal
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl("value1", "value2", "value3")
    val greetingService = new GreetingServiceImpl(greetingDao)
    new AppImpl(greetingService)
  }

}
