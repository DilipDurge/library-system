package com.system.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.system.library.model.Book;
import com.system.library.model.Borrower;
import com.system.library.repository.BookRepository;
import com.system.library.repository.BorrowerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Borrower borrower;

    @BeforeEach
    public void setUp() {

        book = Book.builder()
                .id(1L)
                .isbn("1234567890")
                .title("Java")
                .author("Kathy Sierra")
                .build();

        borrower = Borrower.builder()
                .id(1L)
                .name("Tom Cruise")
                .email("tom.c@gmail.com")
                .build();
    }

    @Test
    public void testBorrowBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book borrowedBook = bookService.borrowBook(1L, 1L);

        assertNotNull(borrowedBook.getBorrower());
        assertEquals("Tom Cruise", borrowedBook.getBorrower().getName());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testBorrowBookAlreadyBorrowed() {
        book.setBorrower(borrower);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(Exception.class, () -> bookService.borrowBook(1L, 1L));

        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    public void testBorrowBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> bookService.borrowBook(1L, 1L));

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testBorrowerNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> bookService.borrowBook(1L, 1L));

        assertEquals("Borrower not found", exception.getMessage());
    }

    @Test
    public void testReturnBook() {
        book.setBorrower(borrower);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book returnedBook = bookService.returnBook(1L, 1L);

        assertNull(returnedBook.getBorrower());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testReturnBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> bookService.returnBook(1L, 1L));

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testReturnBookNotBorrowed() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(Exception.class, () -> bookService.returnBook(1L, 1L));

        assertEquals("Book was not borrowed by this borrower", exception.getMessage());
    }
}

