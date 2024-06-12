package com.system.library.service;

import com.system.library.exception.LibraryCustomException;
import com.system.library.model.Book;
import com.system.library.model.Borrower;
import com.system.library.repository.BookRepository;
import com.system.library.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final BorrowerRepository borrowerRepository;

    public BookService(BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    /**
     * Register a book.
     * @param book
     * @return Book
     */
    public Book registerBook(Book book) {
        Optional<List<Book>> existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            Book existing = existingBook.get().get(1);
            if (!existing.getTitle().equals(book.getTitle()) || !existing.getAuthor().equals(book.getAuthor())) {
                throw new LibraryCustomException("A book with this ISBN already exists but with different title/author.");
            }
        }
        return bookRepository.save(book);
    }

    /**
     * Get all books.
     * @return List<Book>
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Borrow a book.
     * @param borrowerId
     * @param bookId
     * @return Book
     */
    public Book borrowBook(Long borrowerId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new LibraryCustomException("Book not found");
        }

        Book book = bookOptional.get();

        if (book.getBorrower() != null) {
            throw new LibraryCustomException("Book is already borrowed");
        }

        Optional<Borrower> borrowerOptional = borrowerRepository.findById(borrowerId);
        if (!borrowerOptional.isPresent()) {
            throw new LibraryCustomException("Borrower not found");
        }

        Borrower borrower = borrowerOptional.get();
        book.setBorrower(borrower);
        return bookRepository.save(book);
    }

    /**
     * Return a book.
     * @param borrowerId
     * @param bookId
     * @return Book
     */
    public Book returnBook(Long borrowerId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new LibraryCustomException("Book not found");
        }

        Book book = bookOptional.get();

        if (book.getBorrower() == null || !book.getBorrower().getId().equals(borrowerId)) {
            throw new LibraryCustomException("Book was not borrowed by this borrower");
        }

        book.setBorrower(null);
        return bookRepository.save(book);
    }
}

