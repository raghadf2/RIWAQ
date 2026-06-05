package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAnalysisDTOOut {
    private Integer postId;
    private String summary;
    private String postType;
    private Boolean analysisGenerated;
    private LocalDateTime analyzedAt;
}
