package ru.otus.sc.book.dao.map

import java.util.UUID

import ru.otus.sc.book.dao.BookDao
import ru.otus.sc.book.model.Book

class BookDaoMapImpl extends BookDao {
  private var books: Map[UUID, Book] = Map.empty

  def createBook(book: Book): Book = {
    val id         = UUID.randomUUID()
    val bookWithId = book.copy(id = Some(id))
    books += (id -> bookWithId)
    bookWithId
  }

  def getBook(bookId: UUID): Option[Book] = books.get(bookId)

  def updateBook(book: Book): Option[Book] =
    for {
      id <- book.id
      _  <- books.get(id)
    } yield {
      books += (id -> book)
      book
    }

  def deleteBook(bookId: UUID): Option[Book] =
    books.get(bookId) match {
      case Some(book) =>
        books -= bookId
        Some(book)
      case None => None
    }

  def findByTitle(title: String): Seq[Book] =
    books.values.filter(_.title == title).toList

  def findAll(): Seq[Book] = books.values.toVector

  def deleteAll(): Int = {
    val size = books.size
    books = Map.empty
    size
  }

  def findByAuthorName(name: String): Seq[Book] =
    books.values.filter(_.author.name == name).toList

  def findByYear(year: Int): Seq[Book] =
    books.values.filter(_.year == year).toList

  def findByPages(pages: Int, upperLimit: Boolean): Seq[Book] = {
    def predicate =
      if (upperLimit) (book: Book) => book.pages <= pages else (book: Book) => book.pages >= pages
    books.values.filter(predicate).toList
  }
}
