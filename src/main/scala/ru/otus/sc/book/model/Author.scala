package ru.otus.sc.book.model

import java.util.UUID

case class Author(
    id: Option[UUID],
    name: String
)
