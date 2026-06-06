package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.In.SpaceDTOIn;
import com.example.riwaq.Service.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping("/add/{bookId}/{creatorId}")
    public ResponseEntity<?> addSpace(@PathVariable Integer bookId, @PathVariable Integer creatorId, @RequestBody @Valid SpaceDTOIn dto) {
        spaceService.addSpace(bookId, creatorId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Space added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllSpaces(){
        return ResponseEntity.status(200).body(spaceService.getAllSpaces());
    }

    @PutMapping("/update/{spaceId}")
    public ResponseEntity<?> updateSpace(@PathVariable Integer spaceId, @RequestBody @Valid SpaceDTOIn dto){
        spaceService.updateSpace(spaceId,dto);
        return ResponseEntity.status(200).body(new ApiResponse("Space updated successfully"));
    }

    @DeleteMapping("/delete/{spaceId}/{requesterId}")
    public ResponseEntity<?> deleteSpace(@PathVariable Integer spaceId, @PathVariable Integer requesterId) {
        spaceService.deleteSpace(spaceId, requesterId);
        return ResponseEntity.status(200).body(new ApiResponse("Space deleted successfully"));
    }

    //===============

    @GetMapping("/suggestions/{bookId}")
    public ResponseEntity<?> suggestSpaceQuestions(@PathVariable Integer bookId) {
        return ResponseEntity.status(200).body(spaceService.suggestSpaceQuestions(bookId));
    }

    @GetMapping("/get/status/{userId}/{status}")
    public ResponseEntity<?> getSpacesByUserBookStatus(@PathVariable Integer userId, @PathVariable String status) {
        return ResponseEntity.status(200).body(spaceService.getSpacesByUserBookStatus(userId, status));
    }

    @GetMapping("/reflection/{bookId}/{pageNumber}")
    public ResponseEntity<?> generateReflectionPrompts(@PathVariable Integer bookId, @PathVariable Integer pageNumber) {
        return ResponseEntity.status(200).body(spaceService.generateReflectionPrompts(bookId, pageNumber));
    }

}
