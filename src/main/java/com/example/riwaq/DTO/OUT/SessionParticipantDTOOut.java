package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionParticipantDTOOut {

    private Integer participantId;
    private Integer sessionId;
    private Integer userId;
}
