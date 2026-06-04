package com.example.riwaq.Controller;

import com.example.riwaq.DTO.IN.PostDTOIn;
import com.example.riwaq.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody @Valid PostDTOIn dto) {
        postService.addPost(dto);
        return ResponseEntity.ok("Post added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @RequestBody @Valid PostDTOIn dto) {
        postService.updatePost(id, dto);
        return ResponseEntity.ok("Post updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
