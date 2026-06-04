package com.example.riwaq.DTO.IN;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDTOIn {

    @NotNull(message = "User ID must not be null")
    private Integer userId;

    @NotNull(message = "Post ID must not be null")
    private Integer postId;
}
