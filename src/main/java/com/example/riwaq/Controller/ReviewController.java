package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.ReviewDTOIn;
import com.example.riwaq.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Integer id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/filter/highestRated")
    public ResponseEntity<?> getBestRatedReviews() {
        return ResponseEntity.ok(reviewService.getBestRatedReviews());
    }

    @GetMapping("/filter/lowestRated")
    public ResponseEntity<?> getLowestRatedReviews() {
        return ResponseEntity.ok(reviewService.getLowestRatedReviews());
    }

    @GetMapping("/filter/recent")
    public ResponseEntity<?> getRecentReviews() {
        return ResponseEntity.ok(reviewService.getRecentReviews());
    }

    @PostMapping("/add/{userId}/{bookId}")
    public ResponseEntity<?> addReview(@PathVariable Integer userId, @PathVariable Integer bookId, @RequestBody @Valid ReviewDTOIn dto) {
        reviewService.addReview(userId, bookId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @GetMapping("/get/book/{bookId}")
    public ResponseEntity<?> getReviewsByBookId(@PathVariable Integer bookId) {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody @Valid ReviewDTOIn dto) {
        reviewService.updateReview(id, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }
}
