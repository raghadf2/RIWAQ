package com.example.riwaq.Controller;

import com.example.riwaq.DTO.IN.FriendshipDTOIn;
import com.example.riwaq.Service.FriendshipService;
import jakarta.validation.Valid;
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

    @PostMapping("/add")
    public ResponseEntity<?> addFriendship(@RequestBody @Valid FriendshipDTOIn dto) {
        friendshipService.addFriendship(dto);
        return ResponseEntity.ok("Friendship added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFriendship(@PathVariable Integer id, @RequestBody @Valid FriendshipDTOIn dto) {
        friendshipService.updateFriendship(id, dto);
        return ResponseEntity.ok("Friendship updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFriendship(@PathVariable Integer id) {
        friendshipService.deleteFriendship(id);
        return ResponseEntity.ok("Friendship deleted successfully");
    }
}
