package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.PostLikeDTOIn;
import com.example.riwaq.DTO.OUT.PostLikeDTOOut;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Model.PostLike;
import com.example.riwaq.Repository.PostLikeRepository;
import com.example.riwaq.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public List<PostLikeDTOOut> getAllPostLikes() {
        return postLikeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostLikeDTOOut getPostLikeById(Integer id) {
        PostLike postLike = postLikeRepository.findPostLikeById(id);
        if (postLike == null) {
            throw new ApiException("Post like not found");
        }
        return convertToDTO(postLike);
    }

    public void addPostLike(PostLikeDTOIn dto) {
        PostLike postLike = new PostLike();
        postLike.setUserId(dto.getUserId());
        postLike.setPostId(dto.getPostId());
        postLikeRepository.save(postLike);

        Post post = postRepository.findPostById(dto.getPostId());
        if (post != null) {
            post.setLikeCounter(post.getLikeCounter() + 1);
            postRepository.save(post);
        }
    }

    public void updatePostLike(Integer id, PostLikeDTOIn dto) {
        PostLike postLike = postLikeRepository.findPostLikeById(id);
        if (postLike == null) {
            throw new ApiException("Post like not found");
        }
        postLike.setUserId(dto.getUserId());
        postLike.setPostId(dto.getPostId());
        postLikeRepository.save(postLike);
    }

    public void deletePostLike(Integer id) {
        PostLike postLike = postLikeRepository.findPostLikeById(id);
        if (postLike == null) {
            throw new ApiException("Post like not found");
        }

        Post post = postRepository.findPostById(postLike.getPostId());
        if (post != null && post.getLikeCounter() > 0) {
            post.setLikeCounter(post.getLikeCounter() - 1);
            postRepository.save(post);
        }

        postLikeRepository.delete(postLike);
    }

    private PostLikeDTOOut convertToDTO(PostLike postLike) {
        return new PostLikeDTOOut(
                postLike.getId(),
                postLike.getUserId(),
                postLike.getPostId(),
                postLike.getCreatedAt()
        );
    }
}
