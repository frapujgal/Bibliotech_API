package com.fpg.bibliotech_api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpg.bibliotech_api.model.Book;
import com.fpg.bibliotech_api.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books")
public class BookController {
    @Autowired
    BookService bookService;

    @Operation(summary = "Get all books", description = "Get all books or filter by author, title, year or genre")
    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String author,
                                                    @RequestParam(required = false) String title,
                                                    @RequestParam(required = false) Integer year,
                                                    @RequestParam(required = false) String genre) {
        try {
            List<Book> books;
            
            if (author != null && title != null && year != null && genre != null) {
                System.out.println("REQUEST: Getting books from author '" + author + "', with title '" + title + "', year '" + year + "' and genre '" + genre + "'");
                books = bookService.getBooksByAuthorTitleYearAndGenre(author, title, year, genre);
            } 
            else if (author != null && title != null) {
                System.out.println("REQUEST: Getting all books from author '" + author + "' with title '" + title + "'");
                books = bookService.getBooksByTitleAndAuthor(title, author);
            } else if (author != null) {
                System.out.println("REQUEST: Getting all books from author '" + author + "'");
                books = bookService.getBooksByAuthor(author);
            } else if (title != null) {
                System.out.println("REQUEST: Getting all books with title '" + title + "'");
                books = bookService.getBooksByTitle(title);
            } else if (year != null) {
                System.out.println("REQUEST: Getting books published in year '" + year + "'");
                books = bookService.getBooksByPublicationYear(year);
            } else if (genre != null) {
                System.out.println("REQUEST: Getting books with genre '" + genre + "'");
                books = bookService.getBooksByGenre(genre);
            } else {
                System.out.println("REQUEST: Getting all books");
                books = bookService.getAllBooks();
            }
            
            System.out.println("\t- " + books.size() + " books found");
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get book by id")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        System.out.println("REQUEST: Getting book with id " + id);

        try {
            Book book = bookService.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get available books")
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        System.out.println("REQUEST: Getting available books");

        try {
            List<Book> books = bookService.getAvailableBooks();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get unavailable books")
    @GetMapping("/unavailable")
    public ResponseEntity<List<Book>> getUnavailableBooks() {
        System.out.println("REQUEST: Getting unavailable books");

        try {
            List<Book> books = bookService.getUnavailableBooks();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    @Operation(summary = "Get books by author")
    @GetMapping("/author")
	public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author) {
        System.out.println("REQUEST: Getting books from author " + author);

		try {
            List<Book> books = bookService.getBooksByAuthor(author);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    */

    @Operation(summary = "Add new book")
    @PostMapping("")
	public ResponseEntity<Book> add(@RequestBody Book book) {
		System.out.println("REQUEST: Adding new book...");

        Book newBook = bookService.addBook(book);
		System.out.println("\t- Book added with id: " + newBook.getId());
		return new ResponseEntity<>(newBook, HttpStatus.CREATED);
	}

    @Operation(summary = "Delete book")
    @DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
        System.out.println("REQUEST: Deleting book...");

		try {
			bookService.deleteBook(id);
            System.out.println("\t- Book deleted with id: " + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
    
}
