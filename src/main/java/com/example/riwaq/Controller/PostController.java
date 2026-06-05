package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
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
        return ResponseEntity.status(200).body(postService.getAllPosts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/get/page/{userBookId}/{currentPage}")
    public ResponseEntity<?> getPostsByCurrentPage(@PathVariable Integer userBookId, @PathVariable Integer currentPage) {
        return ResponseEntity.ok(postService.getPostsByCurrentPage(userBookId, currentPage));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getPostsByBookId(@PathVariable Integer bookId) {
        return ResponseEntity.ok(postService.getPostsByBookId(bookId));
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<?> getPostsFromFriends(@PathVariable Integer userId) {
        return ResponseEntity.ok(postService.getPostsFromFriends(userId));
    }

    @GetMapping("/filter/most-liked")
    public ResponseEntity<?> getMostLikedPosts() {
        return ResponseEntity.ok(postService.getMostLikedPosts());
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addPost(@PathVariable Integer userId, @RequestBody @Valid PostDTOIn dto) {
        postService.addPost(userId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Post added successfully"));
    }

    @PutMapping("/update/{id}/{userId}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @PathVariable Integer userId, @RequestBody @Valid PostDTOIn dto) {
        postService.updatePost(id, userId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Post updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.status(200).body(new ApiResponse("Post deleted successfully"));
    }
}
