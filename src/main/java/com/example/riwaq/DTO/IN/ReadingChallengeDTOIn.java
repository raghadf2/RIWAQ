package com.example.riwaq.DTO.In;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingChallengeDTOIn {

    @NotNull(message = "Sender page is required")
    @Min(value = 0, message = "Sender page cannot be negative")
    private Integer senderPage;

    @NotNull(message = "Receiver page is required")
    @Min(value = 0, message = "Receiver page cannot be negative")
    private Integer receiverPage;
}
