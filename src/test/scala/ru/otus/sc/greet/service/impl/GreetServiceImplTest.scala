package ru.otus.sc.greet.service.impl

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.book.dao.map.{AuthorDaoMapImpl, BookDaoMapImpl}
import ru.otus.sc.book.dao.{AuthorDao, BookDao}
import ru.otus.sc.book.dto._
import ru.otus.sc.book.model.{Author, Book}
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}

class GreetServiceImplTest extends AnyFreeSpec {

  val srv = new GreetingServiceImpl(new GreetingDaoImpl)

  "GreetingService tests" - {
    "greet" - {
      "should greet" in {
        srv.greet(GreetRequest("Alex")) shouldBe GreetResponse("Hi Alex !")
      }

      "should panic" in {
        srv.greet(GreetRequest("Alex", isHuman = false)) shouldBe GreetResponse("AAAAAAAAAA!!!!!!")
      }
    }

    "count" - {
      "should count" in {
        val count0 = srv.count
        srv.greet(GreetRequest("Alex"))
        srv.count shouldBe count0 + 1
      }
    }

    "set and get value" - {
      "should return none" in {
        srv.getValue(123) shouldBe None
      }

      "should remember value" in {
        srv.setValue(1, "AAA") shouldBe true
        srv.getValue(1) shouldBe Some("AAA")
        srv.setValue(1, "BBB") shouldBe false
      }
    }
  }
}
