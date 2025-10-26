package com.BookStore.controller;

import com.BookStore.DTO.AuthorDTO;
import com.BookStore.Entity.Author;
import com.BookStore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author Management", description = "APIs for managing authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping
    @Operation(summary = "Get all authors", description = "Retrieve all authors with optional filtering, pagination and sorting")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Specification<Author> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AuthorDTO> authors = authorService.getAuthorsWithFilter(spec, pageable);
        return ResponseEntity.ok(authors.getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by ID", description = "Retrieve a specific author by their ID")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setEmail(author.getEmail());
        authorDTO.setBiography(author.getBio());
        return ResponseEntity.ok(authorDTO);
    }

    @PostMapping("/register")
    @Operation(summary = "Create a new author", description = "Create a new author with the provided details")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
//        log.info("{} {}", authorDTO.getPassword(), authorDTO.getBiography());
        authorDTO.setPassword(encoder.encode(authorDTO.getPassword()));
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.ok(createdAuthor);
    }

    @PostMapping("/login")
    @Operation(summary = "Author login", description = "Authenticate an author with email and password")
    public ResponseEntity<AuthorDTO> loginAuthor(@RequestBody AuthorDTO authorDTO) {
//        authorDTO.setPassword(encoder.encode(authorDTO.getPassword()));
        AuthorDTO loggedInAuthor = authorService.loginAuthor(authorDTO,encoder);
        return ResponseEntity.ok(loggedInAuthor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update author", description = "Update an existing author's details")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author", description = "Delete an author by their ID")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }
}