package com.URL.URL.Shortner.Service.repository;

import com.URL.URL.Shortner.Service.entity.URLMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<URLMapping, Long> {
    Optional<URLMapping> findByOriginalURL(String originalURL);
}
