package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.PostLikeDTOIn;
import com.example.riwaq.DTO.OUT.PostLikeDTOOut;
import com.example.riwaq.Model.PostLike;
import com.example.riwaq.Repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

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
        postLikeRepository.delete(postLike);
    }

    private PostLikeDTOOut convertToDTO(PostLike postLike) {
        return new PostLikeDTOOut(
                postLike.getId(),
                postLike.getUserId(),
                postLike.getPostId()
        );
    }
}
