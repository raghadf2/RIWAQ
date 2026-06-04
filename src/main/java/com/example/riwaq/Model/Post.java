package com.example.riwaq.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String content;

    private Integer pageNumber;

//    private Integer userId;
    //@NotNull : i think making book added optional is better
//    private Integer userBookId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

     @ManyToOne
     @JoinColumn(name = "user_id", insertable = false, updatable = false)
     private User user;

     @ManyToOne
     @JoinColumn(name = "user_book_id", insertable = false, updatable = false)
     private UserBook userBook;
}
