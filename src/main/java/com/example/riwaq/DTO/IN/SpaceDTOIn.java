package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceDTOIn {

    @NotNull(message = "Book id is required")
    private Integer bookId;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min=5,max=20,message ="Name length must stay within 5 to 20 characters")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min=5,max=100,message ="description length must stay within 5 to 100 characters")
    private String description;
}
