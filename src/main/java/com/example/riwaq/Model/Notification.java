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

    @NotEmpty
    private String message;

    @NotEmpty
    @Pattern(regexp = "CREATED|UNREAD|READ|DELETED", message = "Status must be CREATED, UNREAD, READ, or DELETED")
    @Column(nullable = false)
    private String status;

    @NotEmpty
    @Pattern(regexp = "FRIEND_REQUEST|FRIEND_ACCEPTED|POST_LIKE|NEW_REVIEW|GENERAL", message = "Type must be FRIEND_REQUEST, FRIEND_ACCEPTED, POST_LIKE, NEW_REVIEW, or GENERAL")
    @Column(nullable = false)
    private String type;

    private Integer referenceId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    private Integer recipientId;

    private Integer senderId;

     @ManyToOne
     @JoinColumn(name = "recipient_id", insertable = false, updatable = false)
     private User recipient;

     @ManyToOne
     @JoinColumn(name = "sender_id", insertable = false, updatable = false)
     private User sender;
}