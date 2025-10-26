package com.URL.URL.Shortner.Service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "url_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class URLMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 2048)
    private String originalURL;
//    private String shortURL;
    @Column(nullable = false)
    private Long accessCount = 0L;
    private Instant createdAt = Instant.now();

    public URLMapping(String originalUrl) {
        this.originalURL = originalUrl;
        this.accessCount = 0L;
        this.createdAt = Instant.now();
    }
}
