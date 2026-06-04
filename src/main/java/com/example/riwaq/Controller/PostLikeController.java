package com.example.riwaq.Controller;

import com.example.riwaq.DTO.IN.PostLikeDTOIn;
import com.example.riwaq.Service.PostLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post-like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPostLikes() {
        return ResponseEntity.ok(postLikeService.getAllPostLikes());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostLikeById(@PathVariable Integer id) {
        return ResponseEntity.ok(postLikeService.getPostLikeById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPostLike(@RequestBody @Valid PostLikeDTOIn dto) {
        postLikeService.addPostLike(dto);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePostLike(@PathVariable Integer id, @RequestBody @Valid PostLikeDTOIn dto) {
        postLikeService.updatePostLike(id, dto);
        return ResponseEntity.ok("Post like updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePostLike(@PathVariable Integer id) {
        postLikeService.deletePostLike(id);
        return ResponseEntity.ok("Post like deleted successfully");
    }
}
