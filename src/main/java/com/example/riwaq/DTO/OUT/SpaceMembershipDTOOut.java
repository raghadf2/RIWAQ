package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceMembershipDTOOut {

    private Integer membershipId;
    private Integer spaceId;
    private Integer userId;
    private LocalDateTime joinedAt;
}
