package com.fpg.bibliotech_api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpg.bibliotech_api.model.Loan;
import com.fpg.bibliotech_api.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loans")
public class LoanController {
    @Autowired
    LoanService loanService;

    @Operation(summary = "Get all loans")
    @GetMapping("")
    public ResponseEntity<List<Loan>> getAllLoans() {
        System.out.println("REQUEST: Getting all loans");
        List<Loan> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @Operation(summary = "Get loan by id")
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Integer id) {
        System.out.println("REQUEST: Getting loan with id " + id);
        try {
            Loan loan = loanService.getLoanById(id);
            System.out.println("\t- Loan found: " + loan.toString());
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println("\t- Loan not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get loans by user id")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Integer userId) {
        System.out.println("REQUEST: Getting loans for user with id " + userId);
        try {
            List<Loan> loans = loanService.getLoansByUserId(userId);
            System.out.println("\t- Loans found: " + loans.size());
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println("\t- Loans not found for user with id " + userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get loans by book id")
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Loan>> getLoansByBookId(@PathVariable Integer bookId) {
        System.out.println("REQUEST: Getting loans for book with id " + bookId);
        try {
            List<Loan> loans = loanService.getLoansByBookId(bookId);
            System.out.println("\t- Loans found: " + loans.size());
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println("\t- Loans not found for book with id " + bookId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create loan")
    @PostMapping("")
    public ResponseEntity<Loan> createLoan(@RequestParam Integer bookId, @RequestParam Integer userId) {
        System.out.println("REQUEST: Creating loan for book " + bookId + " and user " + userId);
        try {
            Loan loan = loanService.createLoan(bookId, userId);
            System.out.println("\t- Loan created with id " + loan.getId());
            return new ResponseEntity<>(loan, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            System.out.println("\t- " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get last loan by book id")
    @GetMapping("/last/{bookId}")
    public ResponseEntity<Loan> getLastLoanByBookId(@PathVariable Integer bookId) {
        System.out.println("REQUEST: Getting last loan for book with id " + bookId);
        try {
            Loan loan = loanService.getLastLoanByBookId(bookId);
            System.out.println("\t- Last loan found: " + loan.toString());
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println("\t- Last loan not found for book with id " + bookId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Return loan")
    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnLoan(@PathVariable Integer id) {
        System.out.println("REQUEST: Returning loan with id " + id);
        try {
            loanService.returnLoan(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}