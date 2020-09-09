package ru.otus.sc.book.dto

import java.util.UUID

import ru.otus.sc.book.model.Book

case class DeleteBookRequest(bookId: UUID)

sealed trait DeleteBookResponse

object DeleteBookResponse {
  case class Deleted(book: Book)    extends DeleteBookResponse
  case class NotFound(userId: UUID) extends DeleteBookResponse
}
