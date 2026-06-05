package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.ReadingChallengeDTOIn;
import com.example.riwaq.Service.ReadingChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/reading-challenge")
@RequiredArgsConstructor
public class ReadingChallengeController {

    private final ReadingChallengeService readingChallengeService;

    @PostMapping("/add/{bookId}/{senderId}/{receiverId}")
    public ResponseEntity<?> addChallenge(@PathVariable Integer bookId, @PathVariable Integer senderId, @PathVariable Integer receiverId, @RequestBody @Valid ReadingChallengeDTOIn dto) {
        readingChallengeService.addChallenge(bookId, senderId, receiverId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Reading challenge added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllChallenges() {
        return ResponseEntity.status(200).body(readingChallengeService.getAllChallenges());
    }

    @GetMapping("/get/{challengeId}")
    public ResponseEntity<?> getChallengeById(@PathVariable Integer challengeId) {
        return ResponseEntity.status(200).body(readingChallengeService.getChallengeById(challengeId));
    }

    @PutMapping("/update/{challengeId}")
    public ResponseEntity<?> updateChallenge(@PathVariable Integer challengeId, @RequestBody @Valid ReadingChallengeDTOIn dto) {
        readingChallengeService.updateChallenge(challengeId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Reading challenge updated successfully"));
    }

    @DeleteMapping("/delete/{challengeId}/{requesterId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable Integer challengeId, @PathVariable Integer requesterId) {
        readingChallengeService.deleteChallenge(challengeId, requesterId);
        return ResponseEntity.status(200).body(new ApiResponse("Reading challenge deleted successfully"));
    }

    //=============

    @GetMapping("/get/status/{status}")
    public ResponseEntity<?> getChallengesByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(readingChallengeService.getChallengesByStatus(status));
    }

    @GetMapping("/get/date")
    public ResponseEntity<?> getChallengesByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.status(200).body(readingChallengeService.getChallengesByDate(startDate, endDate));
    }

}
