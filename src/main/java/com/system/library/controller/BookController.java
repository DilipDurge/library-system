package com.system.library.controller;

import com.system.library.model.Book;
import com.system.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {
    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Register a book
     * @param book
     * @return Book
     */
    @PostMapping
    public ResponseEntity<Book> registerBook(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.registerBook(book));
    }

    /**
     * Get all books
     * @return List<Book>
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}

