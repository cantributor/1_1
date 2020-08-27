package ru.otus.sc.book.service.impl

import java.util.UUID

import org.scalactic.source.Position
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.book.dao.{AuthorDao, BookDao}
import ru.otus.sc.book.dao.map.{AuthorDaoMapImpl, BookDaoMapImpl}
import ru.otus.sc.book.dto._
import ru.otus.sc.book.model.{Author, Book}
import ru.otus.sc.user.dao.UserDao
import ru.otus.sc.user.model._

class BookServiceImplTest extends AnyFreeSpec with MockFactory with BeforeAndAfterEach {

  val authorDao: AuthorDao = new AuthorDaoMapImpl()
  val bookDao: BookDao     = new BookDaoMapImpl()
  val srv                  = new BookServiceImpl(bookDao, authorDao)

  override protected def afterEach(): Unit = {
    super.afterEach()
    srv.deleteAll()
  }

  "BookServiceTest tests" - {
    "createBook" - {
      "should create book" in {
        val response = srv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100))
        response.book.id shouldNot be(None)
        response.book.title shouldBe "Eugine Onegin"
        response.book.author.id shouldNot be(None)
        response.book.author.name shouldBe "Pushkin"
        response.book.pages shouldBe 100
      }
    }

    "getBook" - {
      "should return book" in {
        val createBookResponse = srv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100))
        val getBookResponse    = srv.getBook(GetBookRequest(createBookResponse.book.id.get))
        getBookResponse shouldBe GetBookResponse.Found(createBookResponse.book)
      }

      "should return NotFound on unknown user" in {
        val id = UUID.randomUUID()
        srv.getBook(GetBookRequest(id)) shouldBe GetBookResponse.NotFound(id)
      }
    }

    "updateBook" - {
      "should update existing book" in {
        val createBookResponse = srv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100))
        val book               = createBookResponse.book
        val modifiedBook       = book.copy(pages = 101)
        srv.updateBook(UpdateBookRequest(modifiedBook)) shouldBe UpdateBookResponse.Updated(
          modifiedBook
        )
      }

      "should return NotFound on unknown book" in {
        val book =
          Book(Some(UUID.randomUUID()), "XXX", Author(Some(UUID.randomUUID()), "Vasya Pupkin"), 666)
        srv.updateBook(UpdateBookRequest(book)) shouldBe UpdateBookResponse.NotFound(book.id.get)
      }

      "should return CantUpdateUserWithoutId on user without id" in {
        val book = Book(None, "XXX", Author(Some(UUID.randomUUID()), "Vasya Pupkin"), 666)
        srv.updateBook(UpdateBookRequest(book)) shouldBe UpdateBookResponse.CantUpdateBookWithoutId
      }
    }

    "deleteBook" - {
      "should delete book" in {
        val createBookResponse = srv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100))
        srv.deleteBook(DeleteBookRequest(createBookResponse.book.id.get)) shouldBe
          DeleteBookResponse.Deleted(createBookResponse.book)
      }

      "should return NotFound on unknown book" in {
        val id = UUID.randomUUID()
        srv.deleteBook(DeleteBookRequest(id)) shouldBe DeleteBookResponse.NotFound(id)
      }
    }

    "findBooks" - {
      "by title" - {
        "should return empty list" in {
          srv.findBooks(FindBooksRequest.ByTitle("XXX")) shouldBe FindBooksResponse.Result(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val createBookResponse =
            srv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100))
          srv.findBooks(FindBooksRequest.ByTitle("Eugine Onegin")) shouldBe
            FindBooksResponse.Result(Seq(createBookResponse.book))
        }
      }
    }
  }
}
