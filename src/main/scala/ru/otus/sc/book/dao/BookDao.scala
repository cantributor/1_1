package ru.otus.sc.book.dao

import java.util.UUID

import ru.otus.sc.book.model.Book

trait BookDao {
  def createBook(book: Book): Book
  def getBook(bookId: UUID): Option[Book]
  def updateBook(book: Book): Option[Book]
  def deleteBook(bookId: UUID): Option[Book]
  def findByTitle(title: String): Seq[Book]
  def findAll(): Seq[Book]
  def deleteAll(): Int
}
