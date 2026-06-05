package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTOOut {
    private Integer id;
    private String content;
    private Integer pageNumber;
    private Integer likeCounter;
    private Integer userId;
    private Integer userBookId;
    private String summary;
    private String postType;
    private Boolean analysisGenerated;
    private LocalDateTime analyzedAt;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SpaceDTOOut {

        private Integer spaceId;
        private Integer bookId;
        private String name;
        private String description;
    }
}
