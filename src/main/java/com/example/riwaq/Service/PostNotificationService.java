package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Model.UserBook;
import com.example.riwaq.Repository.PostRepository;
import com.example.riwaq.Repository.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostNotificationService {

    private final PostRepository postRepository;
    private final UserBookRepository userBookRepository;
    private final NotificationService notificationService;

    public void notifyReadersAboutPost(Integer postId) {
        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new ApiException("Post not found");
        }

        if (post.getUserBook() == null || post.getPageNumber() == null) {
            return;
        }

        Integer bookId = post.getUserBook().getBook().getId();
        List<UserBook> eligibleUserBooks =
                userBookRepository.findUserBooksByBook_IdAndStatusAndCurrentPageGreaterThanEqual(
                        bookId,
                        "READING",
                        post.getPageNumber()
                );

        Set<Integer> notifiedUserIds = new HashSet<>();
        for (UserBook userBook : eligibleUserBooks) {
            Integer readerId = userBook.getUser().getId();
            if (readerId.equals(post.getUser().getId()) || !notifiedUserIds.add(readerId)) {
                continue;
            }

            if (Boolean.TRUE.equals(post.getAnalysisGenerated())) {
                notificationService.sendAnalyzedPostAboutCurrentBookNotification(
                        readerId,
                        userBook.getBook().getTitle(),
                        post.getId(),
                        post.getPostType(),
                        post.getSummary()
                );
            } else {
                notificationService.sendPostAboutCurrentBookNotification(
                        readerId,
                        userBook.getBook().getTitle(),
                        post.getId()
                );
            }
        }
    }
}
