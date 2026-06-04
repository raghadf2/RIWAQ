package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.ReadingChallengeDTOIn;
import com.example.riwaq.Service.ReadingChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reading-challenge")
@RequiredArgsConstructor
public class ReadingChallengeController {

    private final ReadingChallengeService readingChallengeService;

    @PostMapping("/add")
    public ResponseEntity<?> createChallenge(@RequestBody @Valid ReadingChallengeDTOIn dto) {
        readingChallengeService.createChallenge(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Challenge added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllChallenges() {
        return ResponseEntity.status(200).body(readingChallengeService.getAllChallenges());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChallenge(@PathVariable Integer id, @RequestBody @Valid ReadingChallengeDTOIn dto) {
        readingChallengeService.updateChallenge(id,dto);
        return ResponseEntity.status(200).body(new ApiResponse("Challenge updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChallenge(@PathVariable Integer id) {
        readingChallengeService.deleteChallenge(id);
        return ResponseEntity.status(200).body(new ApiResponse("Challenge deleted successfully"));
    }

}
