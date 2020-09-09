package ru.otus.sc.book.service

import ru.otus.sc.book.dto._

trait AuthorService {
  def findAuthors(request: FindAuthorsRequest): FindAuthorsResponse
  def deleteAll(): DeleteAllAuthorsResponse
}
