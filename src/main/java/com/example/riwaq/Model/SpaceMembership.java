package com.example.riwaq.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SpaceMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipId;

    @Column(nullable = false)
    private Integer userId;

    private Integer spaceId;

    private LocalDateTime joinedAt;

    @ManyToOne
    @JsonIgnore
    private Space space;

    @ManyToOne
    private User user;
}
