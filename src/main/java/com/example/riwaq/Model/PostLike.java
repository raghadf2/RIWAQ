package com.example.riwaq.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})}) // one user should not like same post multiple times
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "post_id")
    private Integer postId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

     @ManyToOne
     @JoinColumn(name = "user_id", insertable = false, updatable = false)
     private User user;

     @ManyToOne
     @JoinColumn(name = "post_id", insertable = false, updatable = false)
     private Post post;
}
