package com.example.riwaq.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @NotEmpty(message = "title is required")
    @Size(min = 2, max = 100, message = "title must be between 2 and 100 characters")
    private String title;

    @NotEmpty(message = "author is required")
    private String author;

    @NotNull(message = "page count is required")
    @Positive(message = "page count must be greater than 0")
    private Integer pageCount;
}