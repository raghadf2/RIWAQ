package com.example.riwaq.Repository;

import com.example.riwaq.Model.ReadingChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ReadingChallengeRepository extends JpaRepository<ReadingChallenge, Integer> {

    ReadingChallenge findReadingChallengeById(Integer id);

    List<ReadingChallenge> findReadingChallengesByStatus(String status);

    List<ReadingChallenge> findReadingChallengesByCreatedAtBetween(LocalDateTime  startDate, LocalDateTime  endDate);

    ReadingChallenge findReadingChallengeByBook_IdAndFriendship_IdAndStatusNot(Integer bookId, Integer friendshipId, String status);
}
