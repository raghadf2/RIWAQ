package com.example.riwaq.Repository;

import com.example.riwaq.Model.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession,Integer> {

    List<ReadingSession> findByBookId(Integer bookId);
}
