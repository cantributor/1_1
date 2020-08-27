package ru.otus.sc.book.dto

import java.util.UUID

import ru.otus.sc.book.model.Book

case class UpdateBookRequest(book: Book)

sealed trait UpdateBookResponse
object UpdateBookResponse {
  case class Updated(book: Book)      extends UpdateBookResponse
  case class NotFound(bookId: UUID)   extends UpdateBookResponse
  case object CantUpdateBookWithoutId extends UpdateBookResponse
}
