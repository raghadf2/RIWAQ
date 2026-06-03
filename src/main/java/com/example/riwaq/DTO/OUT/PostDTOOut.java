package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTOOut {
    private Integer id;
    private String content;
    private Integer pageNumber;
    private Integer userId;
    private Integer userBookId;

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
