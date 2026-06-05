package com.example.riwaq.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Integer userId;

    @NotEmpty
    private String message;



    @NotEmpty
    @Pattern(
            regexp = "WELCOME|BOOK_ADDED|BOOK_COMPLETED|SIMILAR_BOOKS|PROGRESS_REMINDER|GENERAL",
            message = "Invalid notification type"
    )     @Column(nullable = false)
    private String type;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Boolean sentByEmail = false;
    private Boolean sentByWhatsApp = false;


}