package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTOOut {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String status;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SessionParticipantDTOOut {

        private Integer participantId;
        private Integer sessionId;
        private Integer userId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SpaceMembershipDTOOut {

        private Integer membershipId;
        private Integer spaceId;
        private Integer userId;
        private LocalDateTime joinedAt;
    }
}
