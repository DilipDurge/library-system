package com.system.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.system.library.model.Borrower;
import com.system.library.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    @Test
    public void testRegisterBorrower() {
        Borrower borrower = Borrower.builder()
                .id(1L)
                .name("Tom Cruise")
                .email("tom.c@gmail.com")
                .build();

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        Borrower savedBorrower = borrowerService.registerBorrower(borrower);

        assertEquals("Tom Cruise", savedBorrower.getName());
        assertEquals("tom.c@gmail.com", savedBorrower.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }
}
