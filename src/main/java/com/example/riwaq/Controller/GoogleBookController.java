package com.example.riwaq.Controller;

import com.example.riwaq.DTO.GoogleBookDto;
import com.example.riwaq.Service.GoogleBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/google-book")
@RequiredArgsConstructor
public class GoogleBookController {

    private final GoogleBookService googleBookService;

    @GetMapping("/search")
    public ResponseEntity searchBook(@RequestParam String title) {
        return ResponseEntity.ok(googleBookService.searchBook(title));
//        return googleBookService.searchBook(title);
    }
}
