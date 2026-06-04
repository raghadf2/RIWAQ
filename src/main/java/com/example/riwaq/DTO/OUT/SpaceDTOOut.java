package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceDTOOut {

    private Integer spaceId;
    private Integer bookId;
    private String name;
    private String description;
}
