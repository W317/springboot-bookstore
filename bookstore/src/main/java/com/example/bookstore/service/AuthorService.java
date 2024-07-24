package com.example.bookstore.service;

import com.example.bookstore.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {
    Set<Author> listAllAuthors();

    void addNewAuthor(Author author);
}
