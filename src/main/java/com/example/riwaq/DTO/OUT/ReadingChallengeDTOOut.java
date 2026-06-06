package com.example.riwaq.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer senderPage;
    private Integer receiverPage;
    private String status;
    private Date createdAt;
    private Date respondedAt;
    private Date completedAt;
}
