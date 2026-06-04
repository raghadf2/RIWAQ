package com.example.riwaq.DTO.IN;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTOIn {

    @NotNull(message = "Sender ID must not be null")
    private Integer senderId;

    @NotNull(message = "Receiver ID must not be null")
    private Integer receiverId;

    @Pattern(regexp = "PENDING|ACCEPTED|BLOCKED|REJECTED", message = "Status must be PENDING, ACCEPTED, BLOCKED, or REJECTED")
    private String status;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReadingSessionDTOIn {

        @NotNull(message = "Book id is required")
        private Integer bookId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SessionParticipantDTOIn {

        @NotNull(message = "Session id is required")
        private Integer sessionId;

        @NotNull(message = "User id is required")
        private Integer userId;
    }
}
