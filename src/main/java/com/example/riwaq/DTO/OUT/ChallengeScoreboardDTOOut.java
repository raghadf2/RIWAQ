package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeScoreboardDTOOut {

    private Integer challengeId;

    private Integer bookId;
    private String bookTitle;
    private Integer pageCount;

    private String status;

    private Integer senderId;
    private String senderUsername;
    private Integer senderPage;
    private Integer senderProgressPercentage;
    private Integer senderRemainingPages;

    private Integer receiverId;
    private String receiverUsername;
    private Integer receiverPage;
    private Integer receiverProgressPercentage;
    private Integer receiverRemainingPages;

    private String currentLeader;

    private Integer winnerId;
    private String winnerUsername;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
