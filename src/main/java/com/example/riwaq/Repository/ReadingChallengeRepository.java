package com.example.riwaq.Repository;

import com.example.riwaq.Model.ReadingChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReadingChallengeRepository extends JpaRepository<ReadingChallenge, Integer> {

    ReadingChallenge findReadingChallengeById(Integer id);

    ReadingChallenge findReadingChallengeByBookIdAndSenderIdAndReceiverIdAndStatusNot(Integer bookId, Integer senderId, Integer receiverId, String status);

    ReadingChallenge findReadingChallengeByBookIdAndReceiverIdAndSenderIdAndStatusNot(Integer bookId, Integer receiverId, Integer senderId, String status);

    List<ReadingChallenge> findReadingChallengesByStatus(String status);

    List<ReadingChallenge> findReadingChallengesByCreatedAtBetween(Date startDate, Date endDate);
}
