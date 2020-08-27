package ru.otus.sc.book.dao.map

import java.util.UUID

import ru.otus.sc.book.dao.AuthorDao
import ru.otus.sc.book.model.Author

class AuthorDaoMapImpl extends AuthorDao {
  private var authors: Map[UUID, Author] = Map.empty

  def createAuthor(author: Author): Author = {
    val id           = UUID.randomUUID()
    val authorWithId = author.copy(id = Some(id))
    authors += (id -> authorWithId)
    authorWithId
  }

  def getAuthor(authorId: UUID): Option[Author] = authors.get(authorId)

  def updateAuthor(author: Author): Option[Author] =
    for {
      id <- author.id
      _  <- authors.get(id)
    } yield {
      authors += (id -> author)
      author
    }

  def deleteAuthor(authorId: UUID): Option[Author] =
    authors.get(authorId) match {
      case Some(author) =>
        authors -= authorId
        Some(author)
      case None => None
    }

  def findByName(name: String): Seq[Author] =
    authors.values.filter(_.name == name).toVector

  def findAll(): Seq[Author] = authors.values.toVector
}
