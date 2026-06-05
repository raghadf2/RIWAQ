package com.example.riwaq.Repository;

import com.example.riwaq.Model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {

    UserBook findUserBookById(Integer id);
    List<UserBook> findUserBooksByUser_Id(Integer userId);

    List<UserBook> findUserBooksByUser_IdAndStatus(
            Integer userId,
            String status
    );

    @Query("""
       SELECT ub
       FROM UserBook ub
       WHERE ub.startedAt BETWEEN ?1 AND ?2
       """)
    List<UserBook> findByStartedAtBetween(
            LocalDate startDate,
            LocalDate endDate
    );
    UserBook findUserBookByUser_IdAndBook_Id(
            Integer userId,
            Integer bookId
    );

    List<UserBook> findUserBooksByUser_IdAndProgressPercentageGreaterThanEqualAndProgressPercentageLessThan(
            Integer userId,
            Integer minProgress,
            Integer maxProgress
    );
    List<UserBook> findUserBooksByBook_IdAndStatusAndCurrentPageGreaterThanEqual(
            Integer bookId,
            String status,
            Integer currentPage
    );
}
