package ru.otus.sc.book.dto

import java.util.UUID

import ru.otus.sc.book.model.Book

case class GetBookRequest(bookId: UUID)

sealed trait GetBookResponse

object GetBookResponse {
  case class Found(book: Book)      extends GetBookResponse
  case class NotFound(bookId: UUID) extends GetBookResponse
}
