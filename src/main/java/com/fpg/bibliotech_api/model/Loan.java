package com.fpg.bibliotech_api.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {

    public enum LoanStatus {LOANED, RETURNED};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "loan_date", nullable = false)
    private Date loanDate;

    @Column(name = "max_return_date", nullable = false)
    private Date maxReturnDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status = LoanStatus.LOANED;

    public Loan() {}

    public Loan(Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = new Date();
        this.maxReturnDate = calculateMaxReturnDate();
        this.status = LoanStatus.LOANED;
    }

    private Date calculateMaxReturnDate() {
        long thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000;
        return new Date(loanDate.getTime() + thirtyDaysInMillis);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getMaxReturnDate() {
        return maxReturnDate;
    }

    public void setMaxReturnDate(Date maxReturnDate) {
        this.maxReturnDate = maxReturnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loan{");
        sb.append("id=").append(id);
        sb.append(", book=").append(book);
        sb.append(", user=").append(user);
        sb.append(", loanDate=").append(loanDate);
        sb.append(", maxReturnDate=").append(maxReturnDate);
        sb.append(", returnDate=").append(returnDate);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

}
