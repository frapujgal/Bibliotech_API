package com.fpg.bibliotech_api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpg.bibliotech_api.model.Comment;
import com.fpg.bibliotech_api.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(summary = "Get all comments", description = "Get all comments or filter by user or book")
    @GetMapping("")
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam(required = false) Integer user,
                                                        @RequestParam(required = false) Integer book) {
        
        List<Comment> comments;

        try {
            if (user != null && book != null) {
                System.out.println("REQUEST: Getting comments by user " + user + " and book " + book);
                comments = commentService.getCommentsByUserIdAndBookId(user, book);
            } else if (user != null) {
                System.out.println("REQUEST: Getting comments by user " + user);
                comments = commentService.getCommentsByUserId(user);
            } else if (book != null) {
                System.out.println("REQUEST: Getting comments by book " + book);
                comments = commentService.getCommentsByBookId(book);
            } else {
                System.out.println("REQUEST: Getting all comments");
                comments = commentService.getAllComments();
            }

            System.out.println("\t- " + comments.size() + " comments found");
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get comment by id")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer id) {
        try {
            Comment comment = commentService.getCommentById(id);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new comment")
    @PostMapping("")
    public ResponseEntity<Comment> addComment(@RequestParam Integer bookId, @RequestParam Integer userId, @RequestParam String text, @RequestParam Integer rating) {
        try {
            Comment comment = commentService.addComment(bookId, userId, text, rating);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
