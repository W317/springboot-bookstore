package com.example.bookstore.service;

import com.example.bookstore.model.Book;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

public interface BookService {
    Set<Book> listAllBooks();

    void addNewBook(Book book);
}
