package com.fpg.bibliotech_api.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpg.bibliotech_api.model.Book;
import com.fpg.bibliotech_api.model.Loan;
import com.fpg.bibliotech_api.model.Loan.LoanStatus;
import com.fpg.bibliotech_api.model.User;
import com.fpg.bibliotech_api.repository.BookRepository;
import com.fpg.bibliotech_api.repository.LoanRepository;
import com.fpg.bibliotech_api.repository.UserRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Loan createLoan(Integer bookId, Integer userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }
        
        book.setAvailable(false);
        System.out.println("El libro ahora está " + book.isAvailable());
        bookRepository.save(book);
        
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(new Date());
        loan.setMaxReturnDate(new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)));
        loan.setStatus(LoanStatus.LOANED);
        return loanRepository.save(loan);
    }

    @Transactional
    public void returnLoan(Integer loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NoSuchElementException("Loan not found"));
        
        if (loan.getStatus() == null) {
            throw new IllegalStateException("Loan status is null");
        }

        if (loan.getStatus().equals(LoanStatus.RETURNED)) {
            throw new IllegalStateException("Loan is already returned");
        }
        
        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(new Date());
        loanRepository.save(loan);

        User user = userRepository.findById(loan.getUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (loan.getReturnDate().after(loan.getMaxReturnDate())) {
            System.out.println(user.getName() + " returned the book after the deadline, penalized by 10 points");
            user.setPoints(user.getPoints() - 10);
        } else {
            if (user.getPoints() < 100) {
                System.out.println(user.getName() + " returned the book on time, rewarded 5 points");
                user.setPoints(user.getPoints() + 5);
            } else {
                System.out.println(user.getName() + " returned the book on time, no points rewarded because user has reached the maximum of 100 points");
            }
        }
        userRepository.save(user);
        
        Book book = bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new NoSuchElementException("Book not found"));
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Integer id) {
        return loanRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }

    public List<Loan> getLoansByUserId(Integer userId) {
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> getLoansByBookId(Integer bookId) {
        return loanRepository.findByBookId(bookId);
    }

    public Loan getLastLoanByBookId(Integer bookId) {
        List<Loan> loans = loanRepository.findByBookId(bookId);
        if (loans.isEmpty()) {
            throw new NoSuchElementException("No loans found for book with id " + bookId);
        }
        return loans.get(loans.size() - 1);
    }

}
