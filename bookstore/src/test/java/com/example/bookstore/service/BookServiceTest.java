package com.example.bookstore.service;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewBook() {
        // Arrange
        Author author = new Author();
        author.setId(20);
        author.setName("J.K. Rowling");

        Book book = new Book();
        book.setTitle("Harry Potter");

        when(authorRepository.findById(anyInt())).thenReturn(java.util.Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        bookService.addNewBook(book);

        // Assert
        verify(bookRepository).save(book);
    }
}
