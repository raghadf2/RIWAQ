package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
//import com.example.riwaq.DTO.GoogleBookDto;
import com.example.riwaq.DTO.GoogleBookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import tools.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class GoogleBookService {

    @Value("${google.books.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();




    public GoogleBookDto searchBook(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q="
                + title
                + "&key="
                + apiKey;

        JsonNode root = restTemplate.getForObject(url, JsonNode.class);

//        if (root == null || !root.has("items")) {
//            throw new ApiException("Book not found");
//        }
        if (root == null || !root.has("items") || root.get("items").size() == 0) {
            throw new ApiException("Book not found");
        }
        JsonNode volumeInfo = root.get("items").get(0).get("volumeInfo");
        if(root.get("items").size() == 0){
            throw new ApiException("Book not found");
        }
        String bookTitle = volumeInfo.has("title") ? volumeInfo.get("title").asText() : null;
        String author = volumeInfo.has("authors") ? volumeInfo.get("authors").get(0).asText() : null;
        Integer pageCount = volumeInfo.has("pageCount") ? volumeInfo.get("pageCount").asInt() : null;

        return new GoogleBookDto(bookTitle, author, pageCount);
    }


}