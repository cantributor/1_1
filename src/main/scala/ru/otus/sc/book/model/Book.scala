package ru.otus.sc.book.model

import java.util.UUID

case class Book(
    id: Option[UUID],
    title: String,
    author: Author,
    year: Int,
    pages: Int
)
