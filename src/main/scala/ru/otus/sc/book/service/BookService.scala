package ru.otus.sc.book.service

import ru.otus.sc.book.dto._

trait BookService {
  def createBook(request: CreateBookRequest): CreateBookResponse
  def getBook(request: GetBookRequest): GetBookResponse
  def updateBook(request: UpdateBookRequest): UpdateBookResponse
  def deleteBook(request: DeleteBookRequest): DeleteBookResponse
  def findBooks(request: FindBooksRequest): FindBooksResponse
  def deleteAll(): DeleteAllBooksResponse
}
