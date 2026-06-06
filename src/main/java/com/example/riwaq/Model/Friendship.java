package com.example.riwaq.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
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

    @Pattern(regexp = "PENDING|ACCEPTED|BLOCKED|REJECTED", message = "Status must be PENDING, ACCEPTED, BLOCKED, or REJECTED")
    @Column(nullable = false)
    private String status = "PENDING";

    @ManyToOne
    @JoinColumn(name = "senderId", insertable = false, updatable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", insertable = false, updatable = false)
    private User receiver;

    @JsonIgnore
    @OneToMany(mappedBy = "friendship")
    private Set<ReadingChallenge> readingChallenges;
}