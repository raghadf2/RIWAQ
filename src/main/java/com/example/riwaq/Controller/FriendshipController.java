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

    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptFriendship(@PathVariable Integer id) {
        friendshipService.acceptFriendship(id);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship accepted successfully"));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectFriendship(@PathVariable Integer id) {
        friendshipService.rejectFriendship(id);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship rejected successfully"));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockFriendship(@PathVariable Integer id) {
        friendshipService.blockFriendship(id);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship blocked successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFriendship(@PathVariable Integer id) {
        friendshipService.deleteFriendship(id);
        return ResponseEntity.status(200).body(new ApiResponse("Friendship deleted successfully"));
    }
}
