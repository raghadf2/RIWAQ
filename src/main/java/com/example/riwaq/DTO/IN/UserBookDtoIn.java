package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookDtoIn {


    @NotNull(message = "current page is required")
    @PositiveOrZero(message = "current page must be zero or greater")
    private Integer currentPage;

}
