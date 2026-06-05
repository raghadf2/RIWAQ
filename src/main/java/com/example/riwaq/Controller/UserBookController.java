package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.UserBookDtoIn;
import com.example.riwaq.Service.UserBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        return ResponseEntity.status(201).body(new ApiResponse("User book added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity getAllUserBooks() {
        return ResponseEntity.status(200).body(userBookService.getAllUserBooks());
    }

    @PutMapping("/update-progress/{userBookId}")
    public ResponseEntity updateProgress(@PathVariable Integer userBookId,
                                         @RequestBody @Valid UserBookDtoIn dto) {
        userBookService.updateProgress(userBookId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Reading progress updated successfully"));
    }

    @DeleteMapping("/delete/{userBookId}")
    public ResponseEntity deleteUserBook(@PathVariable Integer userBookId) {
        userBookService.deleteUserBook(userBookId);
        return ResponseEntity.status(200).body(new ApiResponse("User book deleted successfully"));
    }
    //
    @GetMapping("/status/{userId}/{status}")
    public ResponseEntity getBooksByStatus(
            @PathVariable Integer userId,
            @PathVariable String status){

        return ResponseEntity.status(200)
                .body(userBookService.getBooksByStatus(userId,status));
    }

    @GetMapping("/dashboard/{userId}")
    public ResponseEntity getDashboard(@PathVariable Integer userId){

        return ResponseEntity.status(200)
                .body(userBookService.getDashboard(userId));
    }

    @GetMapping("/between-dates")
    public ResponseEntity getBooksBetweenDates(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate){

        return ResponseEntity.status(200)
                .body(
                        userBookService.getBooksBetweenDates(
                                startDate,
                                endDate
                        )
                );
    }
    @GetMapping("/almost-complete/{userId}")
    public ResponseEntity getAlmostCompletedBooks(
            @PathVariable Integer userId){

        return ResponseEntity.status(200)
                .body(
                        userBookService.getAlmostCompletedBooks(userId)
                );
    }
}