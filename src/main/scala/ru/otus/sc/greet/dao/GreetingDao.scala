package ru.otus.sc.greet.dao

trait GreetingDao {
  def greetingPrefix: String
  def greetingPostfix: String
  def getValue(key: Int): Option[String]
  def setValue(key: Int, value: String): Boolean
}
