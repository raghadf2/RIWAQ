package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingChallengeDTOOut {

    private Integer id;
    private Integer friendshipId;
    private Integer bookId;
    private Integer senderId;
    private Integer receiverId;
    private Integer winnerId;
    private Integer senderPage;
    private Integer receiverPage;
    private String status;
    private LocalDateTime  createdAt;
    private LocalDateTime respondedAt;
    private LocalDateTime  completedAt;
}
