package com.example.riwaq.Repository;

import com.example.riwaq.Model.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionParticipantRepository  extends JpaRepository<SessionParticipant,Integer> {

    List<SessionParticipant> findBySessionId(Integer sessionId);
    List<SessionParticipant> findByUserId(Integer userId);
}
