package com.example.riwaq.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleBookService {

    @Value("${google.books.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public String searchBook(String title) {

        String url =
                "https://www.googleapis.com/books/v1/volumes?q="
                        + title
                        + "&key="
                        + apiKey;

        return restTemplate.getForObject(url, String.class);
    }
}