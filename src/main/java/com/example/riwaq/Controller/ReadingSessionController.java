package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.IN.ReadingSessionDTOIn;
import com.example.riwaq.Service.ReadingSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reading-session")
@RequiredArgsConstructor
public class ReadingSessionController {

    private final ReadingSessionService readingSessionService;

    @PostMapping("/add")
    public ResponseEntity<?> addSession(@RequestBody @Valid com.example.riwaq.DTO.IN.FriendshipDTOIn.ReadingSessionDTOIn dto){
        readingSessionService.addSession(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Session added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllSessions(){
        return ResponseEntity.status(200).body(readingSessionService.getAllSessions());
    }

//    @PutMapping("/update/{sessionId}")
//    public ResponseEntity<?> updateSession(@PathVariable Integer sessionId, @RequestBody @Valid com.example.riwaq.DTO.IN.FriendshipDTOIn.ReadingSessionDTOIn dto){
//        readingSessionService.updateSession(sessionId,dto);
//        return ResponseEntity.status(200).body(new ApiResponse("Session updated successfully"));
//    }
//
//    @DeleteMapping("/delete/{sessionId}")
//    public ResponseEntity<?> deleteSession(@PathVariable Integer sessionId){
//        readingSessionService.deleteSession(sessionId);
//        return ResponseEntity.status(200).body(new ApiResponse("Session deleted successfully"));
//    }

}
