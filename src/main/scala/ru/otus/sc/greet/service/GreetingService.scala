package ru.otus.sc.greet.service

import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}

trait GreetingService {
  def greet(request: GreetRequest): GreetResponse
  def count: Int
  def getValue(key: Int): Option[String]
  def setValue(key: Int, value: String): Boolean
  def getLazyVal: String
}
