package com.example.riwaq.DTO.OUT;

import com.example.riwaq.Model.ReadingSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadingSessionDTOOut {

    private Integer sessionId;
    private Integer bookId;
    private ReadingSession.SessionStatus status;
}
