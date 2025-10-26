package com.BookStore.repository;

import com.BookStore.Entity.Book;
import com.BookStore.Entity.Genre;
// import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByGenre(Genre genre);

    List<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> findByGenreAndPriceBetween(Genre genre, BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByAuthorNameContainingIgnoreCase(@Param("author") String author);
}

