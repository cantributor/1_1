package ru.otus.sc.book.dao

import java.util.UUID

import org.scalacheck.Arbitrary._
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import ru.otus.sc.book.model.{Author, Book}
import ru.otus.sc.user.model.{Role, User}

/**
  * Abstract test class that should bw inherited by tests for any UserDao implementation
  */
abstract class BookDaoTest(name: String, createDao: () => BookDao)
    extends AnyFreeSpec
    with ScalaCheckDrivenPropertyChecks {

  implicit val genBook: Gen[Book] = for {
    id    <- Gen.option(Gen.uuid)
    title <- arbitrary[String]
    pages <- arbitrary[Int]
  } yield Book(
    id = id,
    title = title,
    pages = pages,
    author = Author(None, "Fenimor Cooper"),
    year = 1900
  )

  implicit val arbitraryBook: Arbitrary[Book] = Arbitrary(genBook)

  name - {
    "createBook" - {
      "create any number of books" in {
        forAll { (books: Seq[Book], book: Book) =>
          val dao = createDao()
          books.foreach(dao.createBook)

          val createdBook = dao.createBook(book)
          createdBook.id shouldNot be(book.id)
          createdBook.id shouldNot be(None)

          createdBook shouldBe book.copy(id = createdBook.id)
        }
      }
    }

    "getBook" - {
      "get unknown book" in {
        forAll { (books: Seq[Book], bookId: UUID) =>
          val dao = createDao()
          books.foreach(dao.createBook)

          dao.getBook(bookId) shouldBe None
        }
      }

      "get known book" in {
        forAll { (books1: Seq[Book], book: Book, books2: Seq[Book]) =>
          val dao = createDao()
          books1.foreach(dao.createBook)
          val createdBook = dao.createBook(book)
          books2.foreach(dao.createBook)

          dao.getBook(createdBook.id.get) shouldBe Some(createdBook)
        }
      }
    }

    "updateBook" - {
      "update unknown book - keep all books the same" in {
        forAll { (books: Seq[Book], book: Book) =>
          val dao          = createDao()
          val createdBooks = books.map(dao.createBook)

          dao.updateBook(book) shouldBe None

          createdBooks.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }

      "update known book - keep other books the same" in {
        forAll { (books1: Seq[Book], book1: Book, book2: Book, books2: Seq[Book]) =>
          val dao           = createDao()
          val createdBooks1 = books1.map(dao.createBook)
          val createdBook   = dao.createBook(book1)
          val createdBooks2 = books2.map(dao.createBook)

          val toUpdate = book2.copy(id = createdBook.id)
          dao.updateBook(toUpdate) shouldBe Some(toUpdate)
          dao.getBook(toUpdate.id.get) shouldBe Some(toUpdate)

          createdBooks1.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }

          createdBooks2.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }
    }

    "deleteBook" - {
      "delete unknown book - keep all books the same" in {
        forAll { (books: Seq[Book], bookId: UUID) =>
          val dao          = createDao()
          val createdBooks = books.map(dao.createBook)

          dao.deleteBook(bookId) shouldBe None

          createdBooks.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }

      "delete known book - keep other books the same" in {
        forAll { (books1: Seq[Book], book1: Book, books2: Seq[Book]) =>
          val dao           = createDao()
          val createdBooks1 = books1.map(dao.createBook)
          val createdBook   = dao.createBook(book1)
          val createdBooks2 = books2.map(dao.createBook)

          dao.getBook(createdBook.id.get) shouldBe Some(createdBook)
          dao.deleteBook(createdBook.id.get) shouldBe Some(createdBook)
          dao.getBook(createdBook.id.get) shouldBe None

          createdBooks1.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }

          createdBooks2.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }

    }

    "findByTitle" in {
      forAll { (books1: Seq[Book], title: String, books2: Seq[Book]) =>
        val dao            = createDao()
        val withOtherTitle = books1.filterNot(_.title == title)
        val withTitle      = books2.map(_.copy(title = title))

        withOtherTitle.foreach(dao.createBook)
        val createdWithLasName = withTitle.map(dao.createBook)

        dao.findByTitle(title).toSet shouldBe createdWithLasName.toSet
      }
    }

    "findAll" in {
      forAll { books: Seq[Book] =>
        val dao          = createDao()
        val createdBooks = books.map(dao.createBook)

        dao.findAll().toSet shouldBe createdBooks.toSet
      }
    }
  }

  "deleteAll" in {
    forAll { books: Seq[Book] =>
      val dao          = createDao()
      val createdBooks = books.map(dao.createBook)

      dao.deleteAll() shouldBe createdBooks.size
    }
  }
}
