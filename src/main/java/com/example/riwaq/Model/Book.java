package com.example.riwaq.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Integer pageCount;
    private Integer createdByUserId;
    @Column(nullable = false)
    private String source; // USER_CREATED or GOOGLE_BOOK
    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private Set<UserBook> userBooks;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private Set<Review> reviews;
}
