package ru.otus.sc.book.service.impl

import ru.otus.sc.book.dao.{AuthorDao, BookDao}
import ru.otus.sc.book.dto._
import ru.otus.sc.book.model.{Author, Book}
import ru.otus.sc.book.service.BookService
import ru.otus.sc.user.dao.UserDao
import ru.otus.sc.user.model._
import ru.otus.sc.user.service.UserService

class BookServiceImpl(bookDao: BookDao, authorDao: AuthorDao) extends BookService {
  def createBook(request: CreateBookRequest): CreateBookResponse = {
    val authors = authorDao.findByName(request.authorName)
    val author =
      if (authors.isEmpty) authorDao.createAuthor(Author(None, request.authorName))
      else authors.head
    CreateBookResponse(bookDao.createBook(Book(None, request.title, author, request.pages)))
  }

  def getBook(request: GetBookRequest): GetBookResponse =
    bookDao.getBook(request.bookId) match {
      case Some(book) => GetBookResponse.Found(book)
      case None       => GetBookResponse.NotFound(request.bookId)
    }

  def updateBook(request: UpdateBookRequest): UpdateBookResponse =
    request.book.id match {
      case None => UpdateBookResponse.CantUpdateBookWithoutId
      case Some(bookId) =>
        bookDao.updateBook(request.book) match {
          case Some(book) => UpdateBookResponse.Updated(book)
          case None       => UpdateBookResponse.NotFound(bookId)
        }
    }

  def deleteBook(request: DeleteBookRequest): DeleteBookResponse =
    bookDao
      .deleteBook(request.bookId)
      .map(DeleteBookResponse.Deleted)
      .getOrElse(DeleteBookResponse.NotFound(request.bookId))

  def findBooks(request: FindBooksRequest): FindBooksResponse =
    request match {
      case FindBooksRequest.ByTitle(title) =>
        FindBooksResponse.Result(bookDao.findByTitle(title))
    }

  def deleteAll(): DeleteAllBooksResponse = DeleteAllBooksResponse(bookDao.deleteAll())
}
