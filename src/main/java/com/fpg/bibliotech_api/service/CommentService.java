package com.fpg.bibliotech_api.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpg.bibliotech_api.model.Book;
import com.fpg.bibliotech_api.model.Comment;
import com.fpg.bibliotech_api.model.User;
import com.fpg.bibliotech_api.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public CommentService() {
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = commentRepository.findAll();

        if (comments == null || comments.isEmpty()) {
            throw new NoSuchElementException("No books found");
        }

        return comments;
    }

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comment with id " + id + " not found"));
    }

    public List<Comment> getCommentsByBookId(Integer bookId) {
        List<Comment> comments = commentRepository.findByBookId(bookId);

        if (comments == null || comments.isEmpty()) {
            throw new NoSuchElementException("Comments with bookId " + bookId + " not found");
        }

        return comments;
    }

    public List<Comment> getCommentsByUserId(Integer userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);

        if (comments == null || comments.isEmpty()) {
            throw new NoSuchElementException("Comments with userId " + userId + " not found");
        }

        return comments;
    }

    public List<Comment> getCommentsByUserIdAndBookId(Integer userId, Integer bookId) {
        List<Comment> comments = commentRepository.findByUserIdAndBookId(userId, bookId);

        if (comments == null || comments.isEmpty()) {
            throw new NoSuchElementException("Comments with userId " + userId + " and bookId " + bookId + " not found");
        }

        return comments;
    }

    public Comment addComment(Integer bookId, Integer userId, String text, Integer rating) {
        try {
            Book book = bookService.getBookById(bookId);
            User user = userService.getUserById(userId);
            Comment comment = new Comment(book, user, text, rating);

            return commentRepository.save(comment);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Book or user not found");
        }

    }

}
