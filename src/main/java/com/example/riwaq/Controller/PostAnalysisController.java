package com.example.riwaq.Controller;

import com.example.riwaq.Service.PostAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post-analysis")
@RequiredArgsConstructor
public class PostAnalysisController {

    private final PostAnalysisService postAnalysisService;

    @PostMapping("/analyze/{postId}")
    public ResponseEntity<?> analyzePost(@PathVariable Integer postId) {
        return ResponseEntity.status(200).body(postAnalysisService.analyzePost(postId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostAnalysis(@PathVariable Integer postId) {
        return ResponseEntity.status(200).body(postAnalysisService.getPostAnalysis(postId));
    }
}
