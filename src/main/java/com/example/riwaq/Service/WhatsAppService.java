package com.example.riwaq.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WhatsAppService {

    @Value("${ultramsg.instance-id}")
    private String instanceId;

    @Value("${ultramsg.token}")
    private String token;

    public void sendWhatsAppMessage(String phoneNumber, String message) {

        try {

            phoneNumber = formatPhoneNumber(phoneNumber);

            String url = "https://api.ultramsg.com/" + instanceId + "/messages/chat";

            Map<String, String> body = new HashMap<>();

            body.put("token", token);
            body.put("to", phoneNumber);
            body.put("body", message);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.postForObject(url, body, String.class);

        } catch (Exception e) {

            System.out.println("WhatsApp sending failed: " + e.getMessage());

        }
    }

    private String formatPhoneNumber(String phoneNumber){

        if(phoneNumber == null || phoneNumber.isBlank()){
            throw new RuntimeException("Phone number is empty");
        }

        if(phoneNumber.startsWith("05")){
            return "966" + phoneNumber.substring(1);
        }

        if(phoneNumber.startsWith("+966")){
            return phoneNumber.substring(1);
        }

        return phoneNumber;
    }
}
