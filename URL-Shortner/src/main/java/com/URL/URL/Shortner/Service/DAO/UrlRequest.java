package com.URL.URL.Shortner.Service.DAO;

import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

@Data
public class UrlRequest {
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    UrlRequest(String originalUrl){
        this.originalUrl = originalUrl;
    }
}
