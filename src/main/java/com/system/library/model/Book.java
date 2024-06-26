package com.system.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@Setter
@Getter
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN for the book is mandatory.")
    private String isbn;

    @NotBlank(message = "Title of the book is mandatory")
    private String title;

    @NotBlank(message = "Author of the book is mandatory")
    private String author;

    @ManyToOne
    private Borrower borrower;
}
