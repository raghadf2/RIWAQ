package com.example.riwaq.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
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

//    @NotNull
//    private Integer userId;
//
//    @NotNull
//    private Integer bookId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // I have never used this annotation before, I hope it works properly.
    @Column(updatable = false)
    private LocalDateTime updatedAt;

     @ManyToOne
     @JoinColumn(name = "user_id", insertable = false, updatable = false)
     private User user;

     @ManyToOne
     @JoinColumn(name = "book_id", insertable = false, updatable = false)
     private Book book;
}
