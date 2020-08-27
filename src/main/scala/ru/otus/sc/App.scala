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
import ru.otus.sc.user.model._
import ru.otus.sc.user.service.UserService
import ru.otus.sc.user.service.impl.UserServiceImpl

trait App {

  def greet(request: GreetRequest): GreetResponse
  def count: Int
  def echo(request: GreetRequest): GreetRequest
  def getValue(key: Int): Option[String]
  def setValue(key: Int, value: String): Boolean
  def getLazyVal: String

  def createUser(request: CreateUserRequest): CreateUserResponse
  def getUser(request: GetUserRequest): GetUserResponse
  def updateUser(request: UpdateUserRequest): UpdateUserResponse
  def deleteUser(request: DeleteUserRequest): DeleteUserResponse
  def findUsers(request: FindUsersRequest): FindUsersResponse

  def createBook(request: CreateBookRequest): CreateBookResponse
  def getBook(request: GetBookRequest): GetBookResponse
  def updateBook(request: UpdateBookRequest): UpdateBookResponse
  def deleteBook(request: DeleteBookRequest): DeleteBookResponse
  def findBooks(request: FindBooksRequest): FindBooksResponse
}

object App {
  private class AppImpl(
      greetingService: GreetingService,
      userService: UserService,
      bookService: BookService
  ) extends App {
    def greet(request: GreetRequest): GreetResponse = greetingService.greet(request)
    def count: Int                                  = greetingService.count
    def echo(request: GreetRequest): GreetRequest   = request
    def getValue(key: Int): Option[String]          = greetingService.getValue(key)
    def setValue(key: Int, value: String): Boolean  = greetingService.setValue(key, value)
    def getLazyVal: String                          = greetingService.getLazyVal

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
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)

    val userService = new UserServiceImpl(new UserDaoMapImpl)
    val bookService = new BookServiceImpl(new BookDaoMapImpl, new AuthorDaoMapImpl)

    new AppImpl(greetingService, userService, bookService)
  }

}
