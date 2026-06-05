package com.example.riwaq.DTO.In;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceDTOIn {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min=5,max=20,message ="Name length must stay within 5 to 20 characters")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min=5,max=100,message ="description length must stay within 5 to 100 characters")
    private String description;
}
