package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopRatedBookDTOOut {
    private Integer bookId;
    private String title;
    private String author;
    private Double averageRating;
    private Long reviewCount;
}
