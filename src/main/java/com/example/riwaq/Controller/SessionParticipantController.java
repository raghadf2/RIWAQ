package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.Service.SessionParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/session-participant")
@RequiredArgsConstructor
public class SessionParticipantController {

    private final SessionParticipantService sessionParticipantService;

    @PostMapping("/add")
    public ResponseEntity<?> addParticipant(@RequestBody @Valid com.example.riwaq.DTO.IN.FriendshipDTOIn.SessionParticipantDTOIn dto){
        sessionParticipantService.addParticipant(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Participant added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllParticipants(){
        return ResponseEntity.status(200).body(sessionParticipantService.getAllParticipants());
    }

    /*
    @PutMapping("/update/{participantId}")
    public ResponseEntity<?> updateParticipant(@PathVariable Integer participantId, @RequestBody @Valid SessionParticipantDTOIn dto){
        sessionParticipantService.updateParticipant(participantId,dto);
        return ResponseEntity.status(200).body(new ApiResponse("Participant updated successfully"));
    }

     */

    /*
    @DeleteMapping("/delete/{participantId}")
    public ResponseEntity<?> deleteParticipant(@PathVariable Integer participantId){
        sessionParticipantService.deleteParticipant(participantId);

        return ResponseEntity.status(200).body(new ApiResponse("Participant deleted successfully"));
    }

     */

}
