package com.example.riwaq.Model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer currentPage;
    @Column(nullable = false)
    private String status;
    private LocalDate startedAt;
    private LocalDate finishedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


}
