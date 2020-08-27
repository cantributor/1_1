package ru.otus.sc.book.dto

import ru.otus.sc.book.model.Book

case class CreateBookRequest(title: String, authorName: String, year: Int, pages: Int)

case class CreateBookResponse(book: Book)
