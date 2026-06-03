package com.example.riwaq.Service;

import com.example.riwaq.DTO.DTOIN.SessionParticipantDTOIn;
import com.example.riwaq.DTO.DTOOUT.SessionParticipantDTOOut;
import com.example.riwaq.Model.SessionParticipant;
import com.example.riwaq.Repository.SessionParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionParticipantService {

    private final SessionParticipantRepository sessionParticipantRepository;

    public void addParticipant(SessionParticipantDTOIn dto){

        SessionParticipant participant = new SessionParticipant();

        participant.setSessionId(dto.getSessionId());
        participant.setUserId(dto.getUserId());

        sessionParticipantRepository.save(participant);
    }

    public List<SessionParticipantDTOOut> getAllParticipants(){

        List<SessionParticipant> participants = sessionParticipantRepository.findAll();

        List<SessionParticipantDTOOut> dtoOutList = new ArrayList<>();

        for(SessionParticipant participant : participants){

            SessionParticipantDTOOut dtoOut = new SessionParticipantDTOOut();

            dtoOut.setParticipantId(participant.getParticipantId());
            dtoOut.setSessionId(participant.getSessionId());
            dtoOut.setUserId(participant.getUserId());

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }

    /*
    public void updateParticipant(Integer participantId, SessionParticipantDTOIn dto){

        SessionParticipant participant = sessionParticipantRepository
                .findByParticipantId(participantId)
                .orElseThrow(() -> new ApiException("Participant not found"));

        participant.setSessionId(dto.getSessionId());
        participant.setUserId(dto.getUserId());

        sessionParticipantRepository.save(participant);
    }



    public void deleteParticipant(Integer participantId){

        SessionParticipant participant = sessionParticipantRepository
                .findByParticipantId(participantId)
                .orElseThrow(() -> new ApiException("Participant not found"));

        sessionParticipantRepository.delete(participant);
    }

     */
}
