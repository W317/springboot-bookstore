package com.example.bookstore.service.impl;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    private final Set<Author> authors = new HashSet<>();

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public Set<Author> listAllAuthors() {
        return new HashSet<>(authorRepository.findAll()) ;
    }

    @Override
    public void addNewAuthor(Author author) {
        if (authors.add(author)) {
            authorRepository.save(author);
        } else {
            throw new IllegalArgumentException("Author with name " + author.getName() + " already exists.");
        }
    }
}
