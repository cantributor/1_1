package ru.otus.sc

import ru.otus.sc.book.dao.map.{AuthorDaoMapImpl, BookDaoMapImpl}
import ru.otus.sc.book.dto._
import ru.otus.sc.book.service.BookService
import ru.otus.sc.book.service.impl.BookServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.user.dao.map.UserDaoMapImpl
import ru.otus.sc.user.model.{
  CreateUserRequest,
  CreateUserResponse,
  DeleteUserRequest,
  DeleteUserResponse,
  FindUsersRequest,
  FindUsersResponse,
  GetUserRequest,
  GetUserResponse,
  UpdateUserRequest,
  UpdateUserResponse
}
import ru.otus.sc.user.service.UserService
import ru.otus.sc.user.service.impl.UserServiceImpl

trait App {

  /**
    * Greet requested person
    * @param request - greet request
    * @return the greeting response
    */
  def greet(request: GreetRequest): GreetResponse

  def createUser(request: CreateUserRequest): CreateUserResponse
  def getUser(request: GetUserRequest): GetUserResponse
  def updateUser(request: UpdateUserRequest): UpdateUserResponse
  def deleteUser(request: DeleteUserRequest): DeleteUserResponse
  def findUsers(request: FindUsersRequest): FindUsersResponse

  /**
    * Call counter
    * @return number of calls
    */
  def count: Int

  /**
    * Echo of request
    * @param request - some request
    * @return request
    */
  def echo(request: GreetRequest): GreetRequest

  /**
    * Return value depending on key (1, 2 or 3 only)
    * @param key - one of (1, 2, 3)
    * @return Some(value) if key in (1, 2, 3) None otherwise
    */
  def getValue(key: Int): Option[String]

  /**
    * Set value for key
    * @param key - one of (1, 2, 3)
    * @param value - value for key
    * @return true if value set false if key does not exist
    */
  def setValue(key: Int, value: String): Boolean

  /**
    * Get lazy evaluated value
    * @return some value
    */
  def getLazyVal: String
}

object App {
  private class AppImpl(
      greeting: GreetingService,
      userService: UserService,
      bookService: BookService
  ) extends App {
    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    def count: Int                                  = greeting.count
    def echo(request: GreetRequest): GreetRequest   = request
    def getValue(key: Int): Option[String]          = greeting.getValue(key)
    def setValue(key: Int, value: String): Boolean  = greeting.setValue(key, value)
    def getLazyVal: String                          = greeting.getLazyVal

    def createUser(request: CreateUserRequest): CreateUserResponse = userService.createUser(request)
    def getUser(request: GetUserRequest): GetUserResponse          = userService.getUser(request)
    def updateUser(request: UpdateUserRequest): UpdateUserResponse = userService.updateUser(request)
    def deleteUser(request: DeleteUserRequest): DeleteUserResponse = userService.deleteUser(request)
    def findUsers(request: FindUsersRequest): FindUsersResponse    = userService.findUsers(request)

    def createBook(request: CreateBookRequest): CreateBookResponse = bookService.createBook(request)
    def getBook(request: GetBookRequest): GetBookResponse          = bookService.getBook(request)
    def updateBook(request: UpdateBookRequest): UpdateBookResponse = bookService.updateBook(request)
    def deleteBook(request: DeleteBookRequest): DeleteBookResponse = bookService.deleteBook(request)
    def findBooks(request: FindBooksRequest): FindBooksResponse    = bookService.findBooks(request)
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl("value1", "value2", "value3")
    val greetingService = new GreetingServiceImpl(greetingDao)

    val userService = new UserServiceImpl(new UserDaoMapImpl)
    val bookService = new BookServiceImpl(new BookDaoMapImpl, new AuthorDaoMapImpl)

    new AppImpl(greetingService, userService, bookService)
  }

}
