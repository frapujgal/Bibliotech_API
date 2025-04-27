package com.fpg.bibliotech_api.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpg.bibliotech_api.model.Book;
import com.fpg.bibliotech_api.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookService {
    @Autowired
	private BookRepository bookRepository;

    public BookService() {
	}

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("No books found");
        }

        return books;
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
    }

    public List<Book> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("Book with title " + title + " not found");
        }

        return books;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContaining(author);

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("Book with author " + author + " not found");
        }

        return books;
    }

    public List<Book> getBooksByPublicationYear(int publicationYear) {
        List<Book> books = bookRepository.findByPublicationYear(publicationYear);

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("Book with publication year " + publicationYear + " not found");
        }

        return books;
    }

    public List<Book> getBooksByGenre(String genre) {
        List<Book> books = bookRepository.findByGenre(genre);

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("Book with genre " + genre + " not found");
        }

        return books;
    }

    public List<Book> getBooksByAvailability(boolean available) {
        List<Book> books = bookRepository.findByAvailable(available);

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("Books with availability " + available + " not found");
        }

        return books;
    }

    public boolean isAvailable(Integer id) {
        Book book = getBookById(id);
        return book.isAvailable();
    }

    public List<Book> getBooksByTitleAndAuthor(String title, String author) {
        List<Book> books = bookRepository.findByTitleContainingAndAuthorContaining(title, author);

        if (books == null || books.isEmpty()) {
            throw new NoSuchElementException("Book with title " + title + " and author " + author + " not found");
        }

        return books;
    }

    public List<Book> getBooksByAuthorTitleYearAndGenre(String author, String title, Integer publicationYear, String genre) {
        return bookRepository.findByAuthorContainingAndTitleContainingAndPublicationYearAndGenreContaining(
                author, title, publicationYear, genre);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }

    public List<Book> getUnavailableBooks() {
        return bookRepository.findByAvailable(false);
    }

}
