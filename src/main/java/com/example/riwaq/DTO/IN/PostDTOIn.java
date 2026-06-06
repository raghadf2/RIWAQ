package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTOIn {

    @NotEmpty(message = "Content must not be empty")
    private String content;

    private Integer pageNumber;


}
