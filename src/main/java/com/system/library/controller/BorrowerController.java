package com.system.library.controller;

import com.system.library.model.Book;
import com.system.library.model.Borrower;
import com.system.library.service.BookService;
import com.system.library.service.BorrowerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowers")
@Validated
public class BorrowerController {
    @Autowired
    private final BorrowerService borrowerService;

    @Autowired
    private final BookService bookService;

    public BorrowerController(BorrowerService borrowerService, BookService bookService) {
        this.borrowerService = borrowerService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Borrower> registerBorrower(@Valid @RequestBody Borrower borrower) {
        return ResponseEntity.ok(borrowerService.registerBorrower(borrower));
    }

    @PostMapping("/{borrowerId}/borrow/{bookId}")
    public ResponseEntity<Book> borrowBook(@PathVariable @Min(1) Long borrowerId, @PathVariable @Min(1) Long bookId) {
        return ResponseEntity.ok(bookService.borrowBook(borrowerId, bookId));
    }

    @PostMapping("/{borrowerId}/return/{bookId}")
    public ResponseEntity<Book> returnBook(@PathVariable @Min(1) Long borrowerId, @PathVariable @Min(1) Long bookId) {
        return ResponseEntity.ok(bookService.returnBook(borrowerId, bookId));
    }
}

