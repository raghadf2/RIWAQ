package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTOIn {

    @NotEmpty(message = "Message must not be empty")
    private String message;

    @NotEmpty(message = "Type must not be empty")
    @Pattern(regexp = "FRIEND_REQUEST|FRIEND_ACCEPTED|POST_LIKE|NEW_REVIEW|GENERAL", message = "Type must be FRIEND_REQUEST, FRIEND_ACCEPTED, POST_LIKE, NEW_REVIEW, or GENERAL")
    private String type;

    private Integer referenceId;

    @NotNull(message = "Recipient ID must not be null")
    private Integer recipientId;

    private Integer senderId;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SpaceDTOIn {

        @NotNull(message = "Book id is required")
        private Integer bookId;

        @NotEmpty(message = "Name cannot be empty")
        @Size(min=5,max=20,message ="Name length must stay within 5 to 20 characters")
        private String name;

        @NotEmpty(message = "Description cannot be empty")
        @Size(min=5,max=100,message ="description length must stay within 5 to 100 characters")
        private String description;
    }
}
