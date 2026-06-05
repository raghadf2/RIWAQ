package com.example.riwaq.Repository;

import com.example.riwaq.DTO.OUT.TopRatedBookDTOOut;
import com.example.riwaq.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewById(Integer id);
    Review findReviewByUser_IdAndBook_Id(Integer userId, Integer bookId);
    List<Review> findReviewsByBook_Id(Integer bookId);
    List<Review> findReviewsByUser_Id(Integer userId);
    List<Review> findAllByOrderByRatingDesc();
    List<Review> findAllByOrderByRatingAsc();
    List<Review> findAllByOrderByCreatedAtDesc();

    Integer countReviewsByBook_Id(Integer bookId);

    @Query("""
            select new com.example.riwaq.DTO.OUT.TopRatedBookDTOOut(
                r.book.id,
                r.book.title,
                r.book.author,
                avg(r.rating),
                count(r.id)
            )
            from Review r
            group by r.book.id, r.book.title, r.book.author
            order by avg(r.rating) desc, count(r.id) desc
            """)
    List<TopRatedBookDTOOut> findTopRatedBooks();
}
