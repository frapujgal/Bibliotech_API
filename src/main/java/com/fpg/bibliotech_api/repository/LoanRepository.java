package com.fpg.bibliotech_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpg.bibliotech_api.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByUserId(Integer userId);
    List<Loan> findByBookId(Integer bookId);
    List<Loan> findByStatus(Loan.LoanStatus status);

}