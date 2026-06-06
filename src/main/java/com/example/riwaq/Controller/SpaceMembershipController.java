package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.Service.SpaceMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/space-membership")
@RequiredArgsConstructor
public class SpaceMembershipController {

    private final SpaceMembershipService spaceMembershipService;

    @PostMapping("/add/{spaceId}/{userId}")
    public ResponseEntity<?> addMember(@PathVariable Integer spaceId, @PathVariable Integer userId) {
        spaceMembershipService.addMember(spaceId, userId);
        return ResponseEntity.status(200).body(new ApiResponse("Member added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllMemberships() {
        return ResponseEntity.status(200).body(spaceMembershipService.getAllMemberships());
    }

    @GetMapping("/get/space/{spaceId}")
    public ResponseEntity<?> getMembersBySpace(@PathVariable Integer spaceId) {
        return ResponseEntity.status(200).body(spaceMembershipService.getMembersBySpace(spaceId));
    }

    @DeleteMapping("/delete/{spaceId}/{memberId}/{requesterId}")
    public ResponseEntity<?> removeMember(@PathVariable Integer spaceId, @PathVariable Integer memberId, @PathVariable Integer requesterId) {
        spaceMembershipService.removeMember(spaceId, memberId, requesterId);
        return ResponseEntity.status(200).body(new ApiResponse("Member removed successfully"));
    }

    //====================

    @GetMapping("/friends/{spaceId}/{userId}")
    public ResponseEntity<?> getFriendsInSpace(@PathVariable Integer spaceId, @PathVariable Integer userId) {

        return ResponseEntity.status(200).body(spaceMembershipService.getFriendsInSpace(spaceId, userId));
    }
}
