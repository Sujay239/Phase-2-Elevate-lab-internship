package com.BookStore.controller;

import com.BookStore.DTO.BookDTO;
import com.BookStore.Entity.Book;
import com.BookStore.Entity.Genre;
import com.BookStore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getAll")
    @Operation(summary = "Get all books", description = "Retrieve all books with optional filtering, pagination and sorting")
    public ResponseEntity<List<BookDTO>> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Specification<Book> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"));
            }

            if (genre != null && !genre.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("genre"), genre));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (authorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("author").get("id"), authorId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookDTO> books = bookService.getBooksWithFilter(spec, pageable);
        return ResponseEntity.ok(books.getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get books by author", description = "Retrieve all books by a specific author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthorId(@PathVariable Long authorId) {
        List<BookDTO> books = bookService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/add")
    @Operation(summary = "Create a new book", description = "Create a new book with the provided details")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update an existing book's details")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete a book by its ID")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter/genre")
    public ResponseEntity<List<Book>> filterByGenre(@RequestParam Genre genre) {
        List<Book> booksByGenre = bookService.getBooksByGenre(genre);
        return ResponseEntity.ok(booksByGenre);
    }

    // 3️⃣ Filter by Price Range
    @GetMapping("/filter/price")
    public ResponseEntity<List<Book>> filterByPriceRange(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        List<Book> booksByPriceRange = bookService.getBooksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(booksByPriceRange);
    }

    // 4️⃣ Filter by Both Genre & Price Range
    @GetMapping("/filter/combined")
    public ResponseEntity<List<Book>> filterByGenreAndPrice(
            @RequestParam Genre genre,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Book> booksByGenreAndPriceRange = bookService.getBooksByGenreAndPriceRange(genre, minPrice, maxPrice);
        return ResponseEntity.ok(booksByGenreAndPriceRange);
    }

    // ✅ Search by Book Title
    @GetMapping("/searchByTitle")
    public ResponseEntity<List<BookDTO>> searchByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.searchByTitle(title);
        return ResponseEntity.ok(books);
    }

    // ✅ Search by Author Name
    @GetMapping("/searchByAuthor")
    public ResponseEntity<List<BookDTO>> searchByAuthor(@RequestParam String author) {
        List<BookDTO> books = bookService.searchByAuthor(author);
        return ResponseEntity.ok(books);
    }
}