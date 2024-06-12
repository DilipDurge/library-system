package com.system.library.service;

import com.system.library.exception.LibraryCustomException;
import com.system.library.model.Borrower;
import com.system.library.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowerService {
    @Autowired
    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    /**
     * Register a new borrower
     * @param borrower
     * @return Borrower
     */
    public Borrower registerBorrower(Borrower borrower) {
        Optional<Borrower> existingBorrower = borrowerRepository.findByEmail(borrower.getEmail());
        if (existingBorrower.isPresent()) {
            throw new LibraryCustomException("A borrower with this email already exists.");
        }
        return borrowerRepository.save(borrower);
    }
}

