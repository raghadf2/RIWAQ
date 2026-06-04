package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadingSessionDTOIn {

    @NotNull(message = "Book id is required")
    private Integer bookId;
}
