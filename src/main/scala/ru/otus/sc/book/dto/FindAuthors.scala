package ru.otus.sc.book.dto

import ru.otus.sc.book.model.{Author, Book}

sealed trait FindAuthorsRequest

object FindAuthorsRequest {
  case class ByBookYear(year: Int) extends FindAuthorsRequest
}

sealed trait FindAuthorsResponse

object FindAuthorsResponse {
  case class Result(authors: Seq[Author]) extends FindAuthorsResponse
}
