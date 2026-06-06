package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.Service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllFriendships() {
        return ResponseEntity.ok(friendshipService.getAllFriendships());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getFriendshipById(@PathVariable Integer id) {
        return ResponseEntity.ok(friendshipService.getFriendshipById(id));
    }

    @PostMapping("/add/{senderId}/{receiverId}")
    public ResponseEntity<?> addFriendship(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        friendshipService.addFriendship(senderId, receiverId);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship added successfully"));
    }

    @PutMapping("/accept/{friendshipId}/{userId}")
    public ResponseEntity<?> acceptFriendship(@PathVariable Integer friendshipId, @PathVariable Integer userId) {
        friendshipService.acceptFriendship(friendshipId,userId);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship accepted successfully"));
    }

    @PutMapping("/reject/{friendshipId}/{userId}")
    public ResponseEntity<?> rejectFriendship(@PathVariable Integer friendshipId, @PathVariable Integer userId){

        friendshipService.rejectFriendship(friendshipId,userId);

        return ResponseEntity.status(200).body(new ApiResponse("Friendship rejected successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFriendship(@PathVariable Integer id) {
        friendshipService.deleteFriendship(id);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship deleted successfully"));
    }

    //============
    @GetMapping("/pending/{userId}")
    public ResponseEntity<?> getPendingFriendRequests(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(friendshipService.getPendingFriendRequests(userId));
    }
}
