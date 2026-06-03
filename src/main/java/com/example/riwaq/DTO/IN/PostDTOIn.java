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

    @NotNull(message = "User ID must not be null")
    private Integer userId;

    private Integer userBookId;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SpaceMembershipDTOIn {

        @NotNull(message = "Space id is required")
        private Integer spaceId;

        @NotNull(message = "User id is required")
        private Integer userId;
    }
}
