package com.example.riwaq.Repository;

import com.example.riwaq.Model.ReadingChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingChallengeRepository extends JpaRepository<ReadingChallenge, Integer> {

    ReadingChallenge findReadingChallengeById(Integer id);
}
