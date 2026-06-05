package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceMembershipDTOIn {

    @NotNull(message = "Space id is required")
    private Integer spaceId;

    @NotNull(message = "User id is required")
    private Integer userId;
}
