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

    private LocalDateTime joinedAt;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    @JsonIgnore
    private Space space;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
