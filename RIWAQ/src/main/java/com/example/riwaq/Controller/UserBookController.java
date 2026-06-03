package com.example.riwaq.Controller;

import com.example.riwaq.DTO.In.UserBookDtoIn;
import com.example.riwaq.Service.UserBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-book")
@RequiredArgsConstructor
public class UserBookController {

    private final UserBookService userBookService;

    @PostMapping("/add/{userId}/{bookId}")
    public ResponseEntity addUserBook(@PathVariable Integer userId,
                                      @PathVariable Integer bookId,
                                      @RequestBody @Valid UserBookDtoIn dto) {
        userBookService.addUserBook(userId, bookId, dto);
        return ResponseEntity.status(201).body("User book added successfully");
    }

    @GetMapping("/get")
    public ResponseEntity getAllUserBooks() {
        return ResponseEntity.status(200).body(userBookService.getAllUserBooks());
    }

    @PutMapping("/update-progress/{userBookId}")
    public ResponseEntity updateProgress(@PathVariable Integer userBookId,
                                         @RequestBody @Valid UserBookDtoIn dto) {
        userBookService.updateProgress(userBookId, dto);
        return ResponseEntity.status(200).body("Reading progress updated successfully");
    }

    @DeleteMapping("/delete/{userBookId}")
    public ResponseEntity deleteUserBook(@PathVariable Integer userBookId) {
        userBookService.deleteUserBook(userBookId);
        return ResponseEntity.status(200).body("User book deleted successfully");
    }
}