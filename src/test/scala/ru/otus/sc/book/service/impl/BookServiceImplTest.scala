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
  val authorSrv            = new AuthorServiceImpl(bookDao, authorDao)
  val bookSrv              = new BookServiceImpl(bookDao, authorDao)

  override protected def afterEach(): Unit = {
    super.afterEach()
    bookSrv.deleteAll()
    authorSrv.deleteAll()
  }

  "BookServiceTest tests" - {
    "createBook" - {
      "should create book" in {
        val response = bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 1833, 100))
        response.book.id shouldNot be(None)
        response.book.title shouldBe "Eugine Onegin"
        response.book.author.id shouldNot be(None)
        response.book.author.name shouldBe "Pushkin"
        response.book.pages shouldBe 100
      }
    }

    "getBook" - {
      "should return book" in {
        val createBookResponse =
          bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100, 1833))
        val getBookResponse = bookSrv.getBook(GetBookRequest(createBookResponse.book.id.get))
        getBookResponse shouldBe GetBookResponse.Found(createBookResponse.book)
      }

      "should return NotFound on unknown user" in {
        val id = UUID.randomUUID()
        bookSrv.getBook(GetBookRequest(id)) shouldBe GetBookResponse.NotFound(id)
      }
    }

    "updateBook" - {
      "should update existing book" in {
        val createBookResponse =
          bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100, 1833))
        val book         = createBookResponse.book
        val modifiedBook = book.copy(pages = 101)
        bookSrv.updateBook(UpdateBookRequest(modifiedBook)) shouldBe UpdateBookResponse.Updated(
          modifiedBook
        )
      }

      "should return NotFound on unknown book" in {
        val book =
          Book(
            Some(UUID.randomUUID()),
            "XXX",
            Author(Some(UUID.randomUUID()), "Vasya Pupkin"),
            2020,
            666
          )
        bookSrv.updateBook(UpdateBookRequest(book)) shouldBe UpdateBookResponse.NotFound(
          book.id.get
        )
      }

      "should return CantUpdateUserWithoutId on user without id" in {
        val book = Book(None, "XXX", Author(Some(UUID.randomUUID()), "Vasya Pupkin"), 2020, 666)
        bookSrv.updateBook(
          UpdateBookRequest(book)
        ) shouldBe UpdateBookResponse.CantUpdateBookWithoutId
      }
    }

    "deleteBook" - {
      "should delete book" in {
        val createBookResponse =
          bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100, 1833))
        bookSrv.deleteBook(DeleteBookRequest(createBookResponse.book.id.get)) shouldBe
          DeleteBookResponse.Deleted(createBookResponse.book)
      }

      "should return NotFound on unknown book" in {
        val id = UUID.randomUUID()
        bookSrv.deleteBook(DeleteBookRequest(id)) shouldBe DeleteBookResponse.NotFound(id)
      }
    }

    "findBooks" - {
      "by title" - {
        "should return empty list" in {
          bookSrv.findBooks(FindBooksRequest.ByTitle("XXX")) shouldBe FindBooksResponse.Result(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val createBookResponse =
            bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100, 1833))
          bookSrv.findBooks(FindBooksRequest.ByTitle("Eugine Onegin")) shouldBe
            FindBooksResponse.Result(Seq(createBookResponse.book))
        }
      }

      "by author name" - {
        "should return nont-empty and empty lists" in {
          val createBookResponse =
            bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 100, 1833))
          bookSrv.findBooks(FindBooksRequest.ByAuthorName("Pushkin")) shouldBe
            FindBooksResponse.Result(Seq(createBookResponse.book))
          bookSrv.findBooks(FindBooksRequest.ByAuthorName("Lermontov")) shouldBe
            FindBooksResponse.Result(Seq.empty)
        }
      }

      "ByMinPagesAndMaxPagesOfAuthor" - {
        "should return 3rd book" in {
          bookSrv.createBook(CreateBookRequest("Eugine Onegin", "Pushkin", 1833, 101))
          bookSrv.createBook(CreateBookRequest("Hadji Abrek", "Lermontov", 1833, 7))
          val createBookResponse =
            bookSrv.createBook(CreateBookRequest("Geroy Nashego Vremeni", "Lermontov", 1840, 101))
          bookSrv.findBooks(FindBooksRequest.ByMinPagesAndMaxPagesOfAuthor(100, 10)) shouldBe
            FindBooksResponse.Result(Seq(createBookResponse.book))
        }
      }
    }
  }
}
