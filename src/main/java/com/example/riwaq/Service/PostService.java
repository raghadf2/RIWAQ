package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.PostDTOIn;
import com.example.riwaq.DTO.OUT.PostDTOOut;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Model.User;
import com.example.riwaq.Model.UserBook;
import com.example.riwaq.Repository.PostRepository;
import com.example.riwaq.Repository.UserBookRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;

    public List<PostDTOOut> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostDTOOut getPostById(Integer id) {
        Post post = postRepository.findPostById(id);
        if (post == null) {
            throw new ApiException("Post not found");
        }
        return convertToDTO(post);
    }

    public void addPost(PostDTOIn dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setPageNumber(dto.getPageNumber());
//        post.setUserId(dto.getUserId());
//        post.setUserBookId(dto.getUserBookId());
        User user = userRepository.findUserById(dto.getUserId());
        UserBook userBook = userBookRepository.findUserBookById(dto.getUserBookId());

        post.setUser(user);
        post.setUserBook(userBook);
        postRepository.save(post);
    }

    public void updatePost(Integer id, PostDTOIn dto) {
        Post post = postRepository.findPostById(id);
        if (post == null) {
            throw new ApiException("Post not found");
        }
        post.setContent(dto.getContent());
        post.setPageNumber(dto.getPageNumber());
//        post.setUserId(dto.getUserId());
//        post.setUserBookId(dto.getUserBookId());

        User user = userRepository.findUserById(dto.getUserId());
        UserBook userBook = userBookRepository.findUserBookById(dto.getUserBookId());

        post.setUser(user);
        post.setUserBook(userBook);
        postRepository.save(post);
    }

    public void deletePost(Integer id) {
        Post post = postRepository.findPostById(id);
        if (post == null) {
            throw new ApiException("Post not found");
        }
        postRepository.delete(post);
    }

    private PostDTOOut convertToDTO(Post post) {
        return new PostDTOOut(
                post.getId(),
                post.getContent(),
                post.getPageNumber(),
//                post.getUserId(),
//                post.getUserBookId()
                post.getUser().getId(),
                post.getUserBook().getId()
        );
    }
}
