package com.system.library.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.system.library.exception.LibraryCustomException;
import com.system.library.model.Book;
import com.system.library.model.Borrower;
import com.system.library.service.BookService;
import com.system.library.service.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BorrowerController.class)
public class BorrowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private BookService bookService;

    private Borrower borrower;
    private Book book;

    @BeforeEach
    public void setUp() {
        borrower = Borrower.builder()
                .id(1L)
                .name("Tom Cruise")
                .email("tom.c@gmail.com")
                .build();

        book = Book.builder()
                .id(1L)
                .isbn("1234567890")
                .title("Java")
                .author("Kathy Sierra")
                .borrower(borrower)
                .build();
    }

    @Test
    public void testRegisterBorrower() throws Exception {
        when(borrowerService.registerBorrower(any(Borrower.class))).thenReturn(borrower);

        mockMvc.perform(post("/borrowers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(borrower)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Tom Cruise"))
                .andExpect(jsonPath("$.email").value("tom.c@gmail.com"));
    }

    @Test
    public void testBorrowBook() throws Exception {
        when(bookService.borrowBook(1L, 1L)).thenReturn(book);

        mockMvc.perform(post("/borrowers/1/borrow/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Java"))
                .andExpect(jsonPath("$.author").value("Kathy Sierra"))
                .andExpect(jsonPath("$.borrower.id").value(1L))
                .andExpect(jsonPath("$.borrower.name").value("Tom Cruise"));
    }

    @Test
    public void testReturnBook() throws Exception {
        book.setBorrower(null);
        when(bookService.returnBook(1L, 1L)).thenReturn(book);

        mockMvc.perform(post("/borrowers/1/return/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Java"))
                .andExpect(jsonPath("$.author").value("Kathy Sierra"))
                .andExpect(jsonPath("$.borrower").doesNotExist());
    }

    @Test
    public void testRegisterBorrower_BadRequest() throws Exception {
        Borrower invalidBorrower = Borrower.builder().build();
        invalidBorrower.setId(null); // Invalid name

        when(borrowerService.registerBorrower(any(Borrower.class))).thenThrow(new LibraryCustomException("Invalid name"));

        mockMvc.perform(post("/borrowers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(invalidBorrower)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBorrowBook_BookNotFound() throws Exception {
        when(bookService.borrowBook(1L, 1L)).thenThrow(new LibraryCustomException("Book not found"));

        mockMvc.perform(post("/borrowers/1/borrow/1")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    @Test
    public void testReturnBook_BookNotFound() throws Exception {
        when(bookService.returnBook(1L, 1L)).thenThrow(new LibraryCustomException("Book not found"));

        mockMvc.perform(post("/borrowers/1/return/1")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }
}


