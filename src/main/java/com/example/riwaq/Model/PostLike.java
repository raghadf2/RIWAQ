package com.example.riwaq.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer postId;

     @ManyToOne
     @JoinColumn(name = "user_id", insertable = false, updatable = false)
     private User user;

     @ManyToOne
     @JoinColumn(name = "post_id", insertable = false, updatable = false)
     private Post post;
}
