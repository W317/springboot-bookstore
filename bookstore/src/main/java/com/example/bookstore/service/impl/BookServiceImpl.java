package com.example.bookstore.service.impl;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private final Set<Book> books = new HashSet<>();
    @Override
    public Set<Book> listAllBooks() {
        return new HashSet<>(bookRepository.findAll());
    }

    @Override
    public void addNewBook(Book book) {
        Author author = book.getAuthor();

        Optional<Author> existingAuthor = authorRepository.findByName(author.getName());

        if (existingAuthor.isPresent()) {
            book.setAuthor(existingAuthor.get());
        } else {
            authorRepository.save(author);  // Save the author if it does not exist
        }

        // Check if the book already exists
        if (books.add(book)) {
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book with title " + book.getTitle() + " already exists.");
        }
    }


}
