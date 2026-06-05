package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.OUT.PostAnalysisDTOOut;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostAnalysisService {

    private final PostRepository postRepository;
    private final OpenAIService openAIService;

    public PostAnalysisDTOOut analyzePost(Integer postId) {
        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new ApiException("Post not found");
        }

        String prompt =
                "Analyze this reader post. "
                        + "Return ONLY valid JSON in this exact format: "
                        + "{ \"summary\":\"\", \"postType\":\"\" }. "
                        + "Do not return markdown, code fences, or extra text. "
                        + "Strict language rule for summary: NEVER translate the post content. "
                        + "Write the summary in the same language used by the original post content. "
                        + "If the post is Arabic, the summary must be Arabic. "
                        + "If the post is English, the summary must be English. "
                        + "If the post mixes languages, preserve the dominant language and wording style. "
                        + "Do not add ideas that are not in the post. "
                        + "The summary must be shorter than the original post. "
                        + "Do not copy the original post sentence as-is. "
                        + "If the post is already short, compress its idea into a brief phrase in the same language. "
                        + "The summary must be very short and clear, preferably 5 to 15 words. "
                        + "The postType must be exactly one of: "
                        + "Recommendation, Criticism, Discussion, Question, Reflection, Other. "
                        + "Original post content begins here: <<<"
                        + post.getContent()
                        + ">>>";

        Map<String, String> analysis = openAIService.generateJsonAnalysis(prompt);

        String summary = analysis.get("summary");
        String postType = normalizePostType(analysis.get("postType"));

        post.setSummary(summary);
        post.setPostType(postType);
        post.setAnalysisGenerated(true);
        post.setAnalyzedAt(LocalDateTime.now());

        postRepository.save(post);

        return convertToDTO(post);
    }

    public PostAnalysisDTOOut getPostAnalysis(Integer postId) {
        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new ApiException("Post not found");
        }

        if (!Boolean.TRUE.equals(post.getAnalysisGenerated())) {
            throw new ApiException("Post analysis not generated yet");
        }

        return convertToDTO(post);
    }

    private String normalizePostType(String postType) {
        if (postType == null || postType.isBlank()) {
            return "Other";
        }

        return switch (postType.trim().toLowerCase()) {
            case "recommendation" -> "Recommendation";
            case "criticism" -> "Criticism";
            case "discussion" -> "Discussion";
            case "question" -> "Question";
            case "reflection" -> "Reflection";
            default -> "Other";
        };
    }

    private String getArabicPostType(String postType) {
        if (postType == null || postType.isBlank()) {
            return "أخرى";
        }

        return switch (postType) {
            case "Recommendation" -> "توصية";
            case "Criticism" -> "مراجعة نقدية";
            case "Discussion" -> "نقاش";
            case "Question" -> "سؤال";
            case "Reflection" -> "انطباع شخصي";
            default -> "أخرى";
        };
    }

    private PostAnalysisDTOOut convertToDTO(Post post) {
        return new PostAnalysisDTOOut(
                post.getId(),
                post.getSummary(),
                getArabicPostType(post.getPostType()),
                post.getAnalysisGenerated(),
                post.getAnalyzedAt()
        );
    }
}
