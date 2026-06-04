package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTOIn {

    @NotEmpty(message = "Content must not be empty")
    private String content;

    @NotNull(message = "Rating must not be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

//    @NotNull(message = "User ID must not be null")
//    private Integer userId;
//
//    @NotNull(message = "Book ID must not be null")
//    private Integer bookId;
}
