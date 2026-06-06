package com.example.riwaq.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "friendship_id", nullable = false)
    private Integer friendshipId;

    @Column(nullable = false)
    private Integer bookId;

    @Column(nullable = false)
    private Integer senderId;

    @Column(nullable = false)
    private Integer receiverId;

    @Column(nullable = false)
    private Integer senderPage;

    @Column(nullable = false)
    private Integer receiverPage;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    private Date respondedAt;

    private Date completedAt;

    @ManyToOne
    @JoinColumn(name = "friendship_id", insertable = false, updatable = false)
    private Friendship friendship;
}
