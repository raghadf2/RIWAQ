package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingChallengeDTOOut {

    private Integer friendshipId;
    private Integer bookId;
    private Integer senderId;
    private Integer receiverId;
    private Integer senderPage;
    private Integer receiverPage;
    private String status;
}
