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



<<<<<<< HEAD
    @NotEmpty
    @Pattern(
            regexp = "WELCOME|BOOK_ADDED|BOOK_COMPLETED|SIMILAR_BOOKS|PROGRESS_REMINDER|GENERAL")
=======
//    @NotEmpty
//    @Pattern(
//            regexp = "WELCOME|BOOK_ADDED|BOOK_COMPLETED|SIMILAR_BOOKS|PROGRESS_REMINDER|GENERAL")
>>>>>>> 7db5ceb7db92c5edc5825c5331569a00a21f95a7
    @NotEmpty
    @Pattern(regexp = "UNREAD|READ|DELETED", message = "Status must be UNREAD, READ, or DELETED")
    @Column(nullable = false)
    private String status = "UNREAD";

    @NotEmpty
    @Pattern(
            regexp = "WELCOME|BOOK_ADDED|BOOK_COMPLETED|SIMILAR_BOOKS|POST_ABOUT_CURRENT_BOOK|GENERAL",
            message = "Invalid notification type"
    )
    @Column(nullable = false)
    private String type;

    private Integer referenceId;

    private String referenceType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Boolean sentByEmail = false;
    private Boolean sentByWhatsApp = false;


}