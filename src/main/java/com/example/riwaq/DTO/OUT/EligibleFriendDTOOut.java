package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleFriendDTOOut {

    private Integer userId;
    private String name;
    private String username;

    private String bookStatus;
    private Integer progressPercentage;
}
