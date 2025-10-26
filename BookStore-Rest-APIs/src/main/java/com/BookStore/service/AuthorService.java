package com.BookStore.service;

import com.BookStore.DTO.AuthorDTO;
import com.BookStore.Entity.Author;
import com.BookStore.repository.AuthorRepository;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorDTO> getAllAuthors() {
        List<Author> all = authorRepository.findAll();
        return all.stream().map(this::convertToDTO).toList();
    }

    public Page<AuthorDTO> getAllAuthors(Pageable pageable){
        Page<Author> all = authorRepository.findAll(pageable);
        return all.map(this::convertToDTO);
    }

    public Page<AuthorDTO> getAuthorsWithFilter(Specification<Author> spec , Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(spec, pageable);
        return authors.map(this::convertToDTO);
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id: " + id));
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if (authorRepository.existsByEmail(authorDTO.getEmail())) {
            throw new RuntimeException("Author with email " + authorDTO.getEmail() + " already exists.");
        }
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setBio(authorDTO.getBiography());
//        log.info("{}",authorDTO.getPassword());
        author.setPassword(authorDTO.getPassword());
        Author savedAuthor = authorRepository.save(author);
        return convertToDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setBio(authorDTO.getBiography());

        Author updatedAuthor = authorRepository.save(author);
        return convertToDTO(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setEmail(author.getEmail());
        dto.setBiography(author.getBio());
        dto.setPassword(author.getPassword());
        return dto;
    }

    public AuthorDTO loginAuthor(AuthorDTO authorDTO, BCryptPasswordEncoder encoder) {
        Author author = authorRepository.findByEmail(authorDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Author not found with email: " + authorDTO.getEmail()));
//        log.info("{} , {}",author.getPassword(),authorDTO.getPassword());
        if (!encoder.matches(authorDTO.getPassword(), author.getPassword())) {
//            log.info("Password not  matches for email: {}" , authorDTO.getEmail());
            throw new RuntimeException("Invalid password for email: " + authorDTO.getEmail());
        }
        return convertToDTO(author);
    }
}
