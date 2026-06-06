package com.example.riwaq.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceDTOOut {

    private Integer spaceId;
    private Integer bookId;
    private Integer creatorId;
    private String name;
    private String description;
}
