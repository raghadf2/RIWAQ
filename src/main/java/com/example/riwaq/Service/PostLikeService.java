package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.OUT.PostLikeDTOOut;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Model.PostLike;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.PostLikeRepository;
import com.example.riwaq.Repository.PostRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void addPostLike(Integer userId, Integer postId) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        Post post = postRepository.findPostById(postId);

        if (post == null) {
            throw new ApiException("Post not found");
        }

        PostLike existing = postLikeRepository.findPostLikeByUserIdAndPostId(
                userId,
                postId
        );

        if (existing != null) {
            throw new ApiException("User already liked this post");
        }

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);

        postLikeRepository.save(postLike);

        int likeCounter = post.getLikeCounter() == null ? 0 : post.getLikeCounter();
        post.setLikeCounter(likeCounter + 1);
        postRepository.save(post);
    }

    public void updatePostLike(Integer id, Integer userId, Integer postId) {
        PostLike postLike = postLikeRepository.findPostLikeById(id);
        if (postLike == null) {
            throw new ApiException("Post like not found");
        }
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);
    }

    @Transactional
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
