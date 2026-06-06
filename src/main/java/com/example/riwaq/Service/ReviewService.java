package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.ReviewDTOIn;
import com.example.riwaq.DTO.OUT.ReviewDTOOut;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.Review;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.ReviewRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<ReviewDTOOut> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTOOut getReviewById(Integer id) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new ApiException("Review not found");
        }
        return convertToDTO(review);
    }

    public List<ReviewDTOOut> getBestRatedReviews() {
        List<Review> reviews = reviewRepository.findAllByOrderByRatingDesc();
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found");
        }
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTOOut> getLowestRatedReviews() {
        List<Review> reviews = reviewRepository.findAllByOrderByRatingAsc();
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found");
        }
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTOOut> getRecentReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTOOut> getReviewsByBookId(Integer bookId) {
        Book book = bookRepository.findBookById(bookId);
        if (book == null) {
            throw new ApiException("Book not found");
        }

        List<Review> reviews = reviewRepository.findReviewsByBook_Id(bookId);
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found for this book");
        }

        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTOOut> getReviewsByUserId(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        List<Review> reviews = reviewRepository.findReviewsByUser_Id(userId);
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found for this user");
        }

        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void addReview(Integer userId, Integer bookId, ReviewDTOIn dto) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        Book book = bookRepository.findBookById(bookId);

        if (book == null) {
            throw new ApiException("Book not found");
        }

        Review existingReview = reviewRepository.findReviewByUser_IdAndBook_Id(
                userId,
                bookId
        );

        if (existingReview != null) {
            throw new ApiException("User already reviewed this book");
        }

        Review review = new Review();

        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
    }

    public void updateReview(Integer id, ReviewDTOIn dto) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new ApiException("Review not found");
        }
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        review.setIsEdited(true);

        reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new ApiException("Review not found");
        }
        reviewRepository.delete(review);
    }

    private ReviewDTOOut convertToDTO(Review review) {
        return new ReviewDTOOut(
                review.getId(),
                review.getContent(),
                review.getRating(),
                review.getIsEdited(),
                review.getUser().getId(),
                review.getBook().getId()
        );
    }
}
