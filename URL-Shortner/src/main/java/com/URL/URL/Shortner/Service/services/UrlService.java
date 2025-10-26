package com.URL.URL.Shortner.Service.services;

import com.URL.URL.Shortner.Service.entity.URLMapping;
import com.URL.URL.Shortner.Service.repository.UrlRepository;
import com.URL.URL.Shortner.Service.util.Base62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;


    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    @Transactional
    public String UrlShorten(String originalUrl){
        Optional<URLMapping> byOriginalURL = urlRepository.findByOriginalURL(originalUrl);

        if(byOriginalURL.isPresent()){
            return Base62.encode(byOriginalURL.get().getId());
        }
        URLMapping mapping = new URLMapping(originalUrl);
        URLMapping save = urlRepository.save(mapping);
        return Base62.encode(save.getId());
    }

    @Transactional
    public URLMapping getAndIncrementUrl(String code){
        Long id = Base62.decode(code);
        URLMapping byId = urlRepository.findById(id).orElse(null);
        if(byId != null){
            byId.setAccessCount(byId.getAccessCount() + 1);
            urlRepository.save(byId);
        }

        return byId;
    }

    @Transactional
    public URLMapping getByCode(String code){
        long id = Base62.decode(code);
        return urlRepository.findById(id).orElse(null);
    }
}
