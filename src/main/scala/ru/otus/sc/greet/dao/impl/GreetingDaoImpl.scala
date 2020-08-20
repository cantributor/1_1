package ru.otus.sc.greet.dao.impl

import ru.otus.sc.greet.dao.GreetingDao

class GreetingDaoImpl(
    private var value1: String,
    private var value2: String,
    private var value3: String
) extends GreetingDao {

  val greetingPrefix: String = "Hi"

  val greetingPostfix: String = "!"

  def getValue(key: Int): Option[String] =
    key match {
      case 1 => Some(value1)
      case 2 => Some(value2)
      case 3 => Some(value3)
      case _ => None
    }

  def setValue(key: Int, value: String): Boolean = {
    key match {
      case 1 => value1 = value; true
      case 2 => value2 = value; true
      case 3 => value3 = value; true
      case _ => false
    }
  }
}
