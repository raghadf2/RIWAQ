package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTOOut {
    private Integer id;
    private String content;
    private Integer pageNumber;
    private Integer userId;
    private Integer userBookId;
    private Integer likeCounter;
}
