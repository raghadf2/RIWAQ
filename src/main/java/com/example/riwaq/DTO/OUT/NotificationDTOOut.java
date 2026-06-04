package com.example.riwaq.DTO.OUT;

import com.example.riwaq.Model.ReadingSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTOOut {
    private Integer id;
    private String message;
    private String status;
    private String type;
    private Integer referenceId;
    private LocalDateTime createdAt;
    private Integer recipientId;
    private Integer senderId;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReadingSessionDTOOut {

        private Integer sessionId;
        private Integer bookId;
        private ReadingSession.SessionStatus status;
    }
}
