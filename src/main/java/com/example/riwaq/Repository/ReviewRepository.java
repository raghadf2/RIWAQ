package com.example.riwaq.Repository;

import com.example.riwaq.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewById(Integer id);
    Review findReviewByUserIdAndBookId(Integer userId, Integer bookId);
    List<Review> findReviewsByBookId(Integer bookId);
    List<Review> findReviewsByUserId(Integer userId);

    Integer countReviewsByBook_Id(Integer bookId);
}
