package com.URL.URL.Shortner.Service.controller;

import com.URL.URL.Shortner.Service.DAO.UrlRequest;
import com.URL.URL.Shortner.Service.entity.URLMapping;
import com.URL.URL.Shortner.Service.services.UrlService;
// import com.URL.URL.Shortner.Service.util.Base62;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
// import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Map<String,String>> shorten(@RequestBody UrlRequest request){
        String originalUrl = request.getOriginalUrl();
        log.info("{}",originalUrl);
        if (originalUrl == null){
            return ResponseEntity.badRequest().body(Map.of("error" , "Original URL is required"));
        }

        String code = urlService.UrlShorten(originalUrl);
        String base = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String shortUrl =  base + "/api/" + code;

        return ResponseEntity.ok().body(Map.of(
                    "shortUrl" , shortUrl,
                    "code" , code,
                    "originalUrl" , originalUrl
        ));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code){
        URLMapping url = urlService.getAndIncrementUrl(code);

        if (url == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        URI uri = URI.create(url.getOriginalURL());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers,HttpStatus.FOUND);
    }

    @GetMapping("/stats/{code}")
    public ResponseEntity<?> getStats(@PathVariable String code){
        URLMapping byCode = urlService.getByCode(code);
        if(byCode == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error" , "URL Not found"));
        }

        return  ResponseEntity.ok(Map.of(
                "code" , code,
                "originalUrl" , byCode.getOriginalURL(),
                "createdAt" , byCode.getCreatedAt().toString(),
                "clicks" , byCode.getAccessCount().toString()
        ));
    }
}
