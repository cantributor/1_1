package ru.otus.sc.book.service.impl

import ru.otus.sc.book.dao.{AuthorDao, BookDao}
import ru.otus.sc.book.dto._
import ru.otus.sc.book.model.{Author, Book}
import ru.otus.sc.book.service.{AuthorService, BookService}

class AuthorServiceImpl(bookDao: BookDao, authorDao: AuthorDao) extends AuthorService {

  def deleteAll(): DeleteAllAuthorsResponse = DeleteAllAuthorsResponse(authorDao.deleteAll())

  def findAuthors(request: FindAuthorsRequest): FindAuthorsResponse =
    request match {
      case FindAuthorsRequest.ByBookYear(year) =>
        val books = bookDao.findByYear(year)
        FindAuthorsResponse.Result(books.map(book => book.author).distinct)
    }
}
