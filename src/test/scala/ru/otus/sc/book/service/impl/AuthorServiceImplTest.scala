package ru.otus.sc.book.service.impl

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.book.dao.map.{AuthorDaoMapImpl, BookDaoMapImpl}
import ru.otus.sc.book.dao.{AuthorDao, BookDao}
import ru.otus.sc.book.dto._
import ru.otus.sc.book.model.{Author, Book}

class AuthorServiceImplTest extends AnyFreeSpec with MockFactory with BeforeAndAfterEach {

  val authorDao: AuthorDao = new AuthorDaoMapImpl()
  val bookDao: BookDao     = new BookDaoMapImpl()
  val authorSrv            = new AuthorServiceImpl(bookDao, authorDao)
  val bookSrv              = new BookServiceImpl(bookDao, authorDao)

  override protected def afterEach(): Unit = {
    super.afterEach()
    bookSrv.deleteAll()
    authorSrv.deleteAll()
  }

  "AuthorServiceTest tests" - {

    "findAuthors" - {
      "by book year" - {
        "should return authors list" in {
          val createBookResponse1 =
            bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 1833, 100))
          val createBookResponse2 =
            bookSrv.createBook(CreateBookRequest("Hadji Abrek", "Lermontov", 1833, 7))
          bookSrv.createBook(CreateBookRequest("Dubrovsky", "Pushkin", 1833, 300))
          authorSrv.findAuthors(FindAuthorsRequest.ByBookYear(1833)) shouldBe FindAuthorsResponse
            .Result(Seq(createBookResponse1.book.author, createBookResponse2.book.author))
        }
      }
    }
  }
}
