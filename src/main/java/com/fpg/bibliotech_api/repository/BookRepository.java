package com.fpg.bibliotech_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpg.bibliotech_api.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>  {
    
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
    List<Book> findByPublicationYear(int publicationYear);
    List<Book> findByGenre(String genre);
    List<Book> findByAvailable(boolean available);

    List<Book> findByTitleContainingAndAuthorContaining(String title, String author);
    List<Book> findByAuthorContainingAndTitleContainingAndPublicationYearAndGenreContaining(
        String author, String title, Integer publicationYear, String genre);
    
}