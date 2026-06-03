package com.example.riwaq.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBookDto {

    private String title;
    private String author;
    private Integer pageCount;
}