package com.fpg.bibliotech_api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
	private Book book;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
    
	private String comment;
    private int rating;
    private Date date;

    public Comment() {
    }

    public Comment(Book book, User user, String comment, int rating) {
        this.book = book;
        this.user = user;
        this.comment = comment;
        this.rating = rating;
        this.date = new Date();
    }

    public Comment(Book book, String comment, Date date, Integer id, int rating, User user) {
        this.book = book;
        this.comment = comment;
        this.date = date;
        this.id = id;
        this.rating = rating;
        this.user = user;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(", bookId=").append(book != null ? book.getId() : null);
        sb.append(", userId=").append(user != null ? user.getId() : null);

        /* 
        sb.append("Comment{");
        sb.append("id=").append(id);
        sb.append(", book=").append(book);
        sb.append(", user=").append(user);
        sb.append(", comment=").append(comment);
        sb.append(", rating=").append(rating);
        sb.append(", date=").append(date);
        sb.append('}');*/
        
        return sb.toString();
    }

}
