package ru.otus.sc.greet.dao.impl

import ru.otus.sc.greet.dao.GreetingDao

class GreetingDaoImpl extends GreetingDao {

  val greetingPrefix: String = "Hi"

  val greetingPostfix: String = "!"

  private var values: Map[Int, String] = Map.empty

  def getValue(key: Int): Option[String] = values.get(key)

  def setValue(key: Int, value: String): Boolean = {
    val foundKey = values.contains(key)
    values += key -> value
    !foundKey
  }
}
