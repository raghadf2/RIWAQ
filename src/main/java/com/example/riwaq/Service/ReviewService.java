package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.ReviewDTOIn;
import com.example.riwaq.DTO.OUT.ReviewDTOOut;
import com.example.riwaq.Model.Review;
import com.example.riwaq.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

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

    public List<ReviewDTOOut> getReviewsByRating(Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new ApiException("Rating must be between 1 and 5");
        }
        List<Review> reviews = reviewRepository.findReviewsByRating(rating);
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found with rating: " + rating);
        }
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTOOut> getRecentReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void addReview(ReviewDTOIn dto) {
        Review review = new Review();
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        review.setUserId(dto.getUserId());
        review.setBookId(dto.getBookId());
        reviewRepository.save(review);
    }

    public void updateReview(Integer id, ReviewDTOIn dto) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new ApiException("Review not found");
        }
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        review.setUserId(dto.getUserId());
        review.setBookId(dto.getBookId());
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
                review.getUserId(),
                review.getBookId()
        );
    }
}