package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    public String generateReaderAnalysis(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(apiKey);

        headers.setContentType(MediaType.APPLICATION_JSON);


        Map<String, Object> body = new HashMap<>();


        body.put("model", "gpt-4o-mini");


        List<Map<String, String>> messages = new ArrayList<>();


        messages.add(Map.of(

                "role", "user",

                "content", prompt

        ));
        body.put("messages", messages);
        HttpEntity<Map<String, Object>> request =

                new HttpEntity<>(body, headers);


        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        try {

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.getBody());

            return root.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {

            throw new ApiException("Failed to parse OpenAI response");
        }


    }
    public Map<String, String> generateJsonAnalysis(String prompt) {

        String aiResponse = generateReaderAnalysis(prompt);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode analysis = mapper.readTree(aiResponse);

            Map<String, String> responseMap = new HashMap<>();

            analysis.fields().forEachRemaining(entry -> {
                responseMap.put(entry.getKey(), entry.getValue().asText());
            });

            return responseMap;

        } catch (Exception e) {
            throw new ApiException("Failed to parse AI JSON response");
        }
    }

}
