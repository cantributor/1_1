package ru.otus.sc.greet.service.impl

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService

class GreetingServiceImpl(dao: GreetingDao) extends GreetingService {
  private var counter = 0
  private lazy val lazyValue = {
    println("lazyValue is evaluated now")
    "LAZY VALUE"
  }

  def greet(request: GreetRequest): GreetResponse = {
    counter += 1
    if (request.isHuman)
      GreetResponse(s"${dao.greetingPrefix} ${request.name} ${dao.greetingPostfix}")
    else GreetResponse("AAAAAAAAAA!!!!!!")
  }

  def count: Int = counter

  def getValue(key: Int): Option[String] = dao.getValue(key)

  def setValue(key: Int, value: String): Boolean = dao.setValue(key, value)

  def getLazyVal: String = lazyValue
}
