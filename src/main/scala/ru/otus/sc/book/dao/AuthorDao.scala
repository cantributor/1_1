package ru.otus.sc.book.dao

import java.util.UUID

import ru.otus.sc.book.model.{Author, Book}

trait AuthorDao {
  def createAuthor(author: Author): Author
  def getAuthor(authorId: UUID): Option[Author]
  def updateAuthor(author: Author): Option[Author]
  def deleteAuthor(authorId: UUID): Option[Author]
  def findByName(name: String): Seq[Author]
  def findAll(): Seq[Author]
  def deleteAll(): Int

}
