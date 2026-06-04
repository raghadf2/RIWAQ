package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTOOut {
    private Integer id;
    private String content;
    private Integer rating;
    private Boolean isEdited;
//    private Integer userId;
//    private Integer bookId;
}
