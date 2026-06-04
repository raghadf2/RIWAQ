package com.example.riwaq.Controller;

import com.example.riwaq.DTO.IN.UserDtoIn;
import com.example.riwaq.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid UserDtoIn dto) {
        userService.addUser(dto);
        return ResponseEntity.status(201).body("User added successfully");
    }

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId,
                                     @RequestBody @Valid UserDtoIn dto) {
        userService.updateUser(userId, dto);
        return ResponseEntity.status(200).body("User updated successfully");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body("User deleted successfully");
    }
    //
    @GetMapping("/username/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username){

        return ResponseEntity.status(200)
                .body(userService.getUserByUsername(username));
    }
}