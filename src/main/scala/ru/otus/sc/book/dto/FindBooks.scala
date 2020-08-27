package ru.otus.sc.book.dto

import ru.otus.sc.book.model.Book

sealed trait FindBooksRequest

object FindBooksRequest {
  case class ByTitle(title: String) extends FindBooksRequest
}

sealed trait FindBooksResponse

object FindBooksResponse {
  case class Result(books: Seq[Book]) extends FindBooksResponse
}
