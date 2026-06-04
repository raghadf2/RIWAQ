package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionParticipantDTOIn {

    @NotNull(message = "Session id is required")
    private Integer sessionId;

    @NotNull(message = "User id is required")
    private Integer userId;
}
