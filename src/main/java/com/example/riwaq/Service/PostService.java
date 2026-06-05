package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.PostDTOIn;
import com.example.riwaq.DTO.OUT.PostDTOOut;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Model.Friendship;
import com.example.riwaq.Model.User;
import com.example.riwaq.Model.UserBook;
import com.example.riwaq.Repository.FriendshipRepository;
import com.example.riwaq.Repository.PostRepository;
import com.example.riwaq.Repository.UserBookRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final FriendshipRepository friendshipRepository;
    private final PostNotificationService postNotificationService;
    private final PostAnalysisService postAnalysisService;

    public List<PostDTOOut> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostDTOOut> dtoOutList = new ArrayList<>();

        for (Post post : posts) {
            dtoOutList.add(convertToDTO(post));
        }

        return dtoOutList;
    }

    public PostDTOOut getPostById(Integer id) {
        Post post = postRepository.findPostById(id);
        if (post == null) {
            throw new ApiException("Post not found");
        }
        return convertToDTO(post);
    }

    public List<PostDTOOut> getPostsByCurrentPage(Integer userBookId, Integer currentPage) {
        List<Post> posts = postRepository.findPostsByUserBook_IdAndPageNumberLessThanEqual(userBookId, currentPage);
        if (posts.isEmpty()) {
            throw new ApiException("No posts found for this book up to page: " + currentPage);
        }
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PostDTOOut> getPostsByBookId(Integer bookId) {
        List<Post> posts = postRepository.findPostsByUserBook_Book_Id(bookId);
        if (posts.isEmpty()) {
            throw new ApiException("No posts found for this book");
        }
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PostDTOOut> getPostsFromFriends(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        List<Friendship> friendships = friendshipRepository.findFriendshipsByUserIdAndStatus(userId, "ACCEPTED");
        if (friendships.isEmpty()) {
            throw new ApiException("No accepted friends found");
        }

        List<Integer> friendIds = friendships.stream()
                .map(friendship -> friendship.getSenderId().equals(userId)
                        ? friendship.getReceiverId()
                        : friendship.getSenderId())
                .collect(Collectors.toList());

        List<Post> posts = postRepository.findPostsByUser_IdInOrderByCreatedAtDesc(friendIds);
        if (posts.isEmpty()) {
            throw new ApiException("No posts found from friends");
        }
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PostDTOOut> getMostLikedPosts() {
        List<Post> posts = postRepository.findAllByOrderByLikeCounterDesc();
        if (posts.isEmpty()) {
            throw new ApiException("No posts found");
        }
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void addPost(Integer userId, PostDTOIn dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setPageNumber(dto.getPageNumber());
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        post.setUser(user);

        if (dto.getUserBookId() != null) {
            UserBook userBook = userBookRepository.findUserBookById(dto.getUserBookId());

            if (userBook == null) {
                throw new ApiException("UserBook not found");
            }

            if (!userBook.getUser().getId().equals(user.getId())) {
                throw new ApiException("UserBook does not belong to this user");
            }

            post.setUserBook(userBook);
        }

        Post savedPost = postRepository.save(post);

        try {
            postAnalysisService.analyzePost(savedPost.getId());
        } catch (Exception e) {
            System.out.println("Post analysis not generated");
        }

        postNotificationService.notifyReadersAboutPost(savedPost.getId());
    }

    public void updatePost(Integer id, Integer userId, PostDTOIn dto) {
        Post post = postRepository.findPostById(id);
        if (post == null) {
            throw new ApiException("Post not found");
        }
        post.setContent(dto.getContent());
        post.setPageNumber(dto.getPageNumber());

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        post.setUser(user);

        if (dto.getUserBookId() != null) {
            UserBook userBook = userBookRepository.findUserBookById(dto.getUserBookId());

            if (userBook == null) {
                throw new ApiException("UserBook not found");
            }

            if (!userBook.getUser().getId().equals(user.getId())) {
                throw new ApiException("UserBook does not belong to this user");
            }

            post.setUserBook(userBook);
        } else {
            post.setUserBook(null);
        }

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
                post.getLikeCounter(),
                post.getUser() == null ? null : post.getUser().getId(),
                post.getUserBook() == null ? null : post.getUserBook().getId(),
                post.getSummary(),
                post.getPostType(),
                post.getAnalysisGenerated(),
                post.getAnalyzedAt()
        );
    }
}
