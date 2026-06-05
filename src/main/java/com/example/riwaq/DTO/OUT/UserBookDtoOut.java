package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookDtoOut {


        private Integer id;
        private Integer userId;
        private Integer bookId;
        private Integer currentPage;
        private String status;
        private LocalDate startedAt;
        private LocalDate finishedAt;
        private Integer progressPercentage;
    }


