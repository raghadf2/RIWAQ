package com.example.riwaq.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String content;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(nullable = false)
    private Boolean isEdited = false;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer bookId;

     @ManyToOne
     @JoinColumn(name = "user_id", insertable = false, updatable = false)
     private User user;

     @ManyToOne
     @JoinColumn(name = "book_id", insertable = false, updatable = false)
     private Book book;
}
