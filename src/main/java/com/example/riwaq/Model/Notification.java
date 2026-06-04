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

//    @NotEmpty
//    @Pattern(regexp = "CREATED|UNREAD|READ|DELETED", message = "Status must be CREATED, UNREAD, READ, or DELETED")
////    @Column(nullable = false)
//    private String status;

    @NotEmpty
    @Pattern(
            regexp = "WELCOME|BOOK_ADDED|BOOK_COMPLETED|GENERAL",
            message = "Invalid notification type"
    )     @Column(nullable = false)
    private String type;

//    private Integer referenceId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Boolean sentByEmail = false;
    private Boolean sentByWhatsApp = false;


}