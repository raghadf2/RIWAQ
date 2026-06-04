package com.example.riwaq.DTO.IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingChallengeDTOIn {

    @NotNull(message = "Friendship ID must not be null")
    private Integer friendshipId;

    @NotNull(message = "Book ID must not be null")
    private Integer bookId;

    @NotNull(message = "Sender ID must not be null")
    private Integer senderId;

    @NotNull(message = "Receiver ID must not be null")
    private Integer receiverId;

    @NotNull(message = "Sender page must not be null")
    @Min(value = 1, message = "Sender page must be greater than 0")
    private Integer senderPage;

    @NotNull(message = "Receiver page must not be null")
    @Min(value = 1, message = "Receiver page must be greater than 0")
    private Integer receiverPage;
}
