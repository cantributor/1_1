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
  def greet(request: GreetRequest): GreetResponse

  def createUser(request: CreateUserRequest): CreateUserResponse
  def getUser(request: GetUserRequest): GetUserResponse
  def updateUser(request: UpdateUserRequest): UpdateUserResponse
  def deleteUser(request: DeleteUserRequest): DeleteUserResponse
  def findUsers(request: FindUsersRequest): FindUsersResponse
}

object App {
  private class AppImpl(
      greeting: GreetingService,
      userService: UserService,
      bookService: BookService
  ) extends App {
    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)

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
