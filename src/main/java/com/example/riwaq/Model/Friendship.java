package com.example.riwaq.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer senderId;

    @NotNull
    private Integer receiverId;

    @NotEmpty
    @Pattern(regexp = "PENDING|ACCEPTED|BLOCKED|REJECTED", message = "Status must be PENDING, ACCEPTED, BLOCKED, or REJECTED")
    @Column(nullable = false)
    private String status;

     @ManyToOne
     @JoinColumn(name = "sender_id", insertable = false, updatable = false)
     private User sender;

     @ManyToOne
     @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
     private User receiver;
}
