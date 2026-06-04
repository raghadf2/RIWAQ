package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.Service.PostLikeService;
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

    @PostMapping("/add/{userId}/{postId}")
    public ResponseEntity<?> addPostLike(@PathVariable Integer userId, @PathVariable Integer postId) {
        postLikeService.addPostLike(userId, postId);
        return ResponseEntity.status(200).body(new ApiResponse("Post liked successfully"));
    }

    @PutMapping("/update/{id}/{userId}/{postId}")
    public ResponseEntity<?> updatePostLike(@PathVariable Integer id, @PathVariable Integer userId, @PathVariable Integer postId) {
        postLikeService.updatePostLike(id, userId, postId);
        return ResponseEntity.status(200).body(new ApiResponse("Post like updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePostLike(@PathVariable Integer id) {
        postLikeService.deletePostLike(id);
        return ResponseEntity.status(200).body(new ApiResponse("Post like deleted successfully"));
    }
}
