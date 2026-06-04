package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.ReadingSessionDTOIn;
import com.example.riwaq.DTO.OUT.ReadingSessionDTOOut;
import com.example.riwaq.Model.ReadingSession;
import com.example.riwaq.Repository.ReadingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingSessionService {

    private final ReadingSessionRepository readingSessionRepository;

    public void addSession(FriendshipDTOIn.ReadingSessionDTOIn dto){

        ReadingSession session = new ReadingSession();

        session.setBookId(dto.getBookId());
        session.setStatus(ReadingSession.SessionStatus.ACTIVE);

        readingSessionRepository.save(session);
    }

    public List<NotificationDTOOut.ReadingSessionDTOOut> getAllSessions(){

        List<ReadingSession> sessions = readingSessionRepository.findAll();

        List<NotificationDTOOut.ReadingSessionDTOOut> dtoOutList = new ArrayList<>();

        for(ReadingSession session : sessions){

            NotificationDTOOut.ReadingSessionDTOOut dtoOut = new NotificationDTOOut.ReadingSessionDTOOut();

            dtoOut.setSessionId(session.getSessionId());
            dtoOut.setBookId(session.getBookId());
            dtoOut.setStatus(session.getStatus());

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }

    public void updateSession(Integer sessionId,
                              ReadingSessionDTOIn dto){

//        ReadingSession session = readingSessionRepository
//                .findBySessionId(sessionId)
//                .orElseThrow(() ->
//                        new ApiException("Session not found"));
//
//        session.setBookId(dto.getBookId());
//
//        readingSessionRepository.save(session);
    }

    public void deleteSession(Integer sessionId){

//        ReadingSession session = readingSessionRepository
//                .findBySessionId(sessionId)
//                .orElseThrow(() ->
//                        new ApiException("Session not found"));
//
//        readingSessionRepository.delete(session);
    }
}
