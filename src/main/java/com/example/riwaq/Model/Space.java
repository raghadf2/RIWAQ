package com.example.riwaq.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spaceId;

    @Column(nullable = false)
    private Integer bookId;

    @Column(nullable = false)
    private Integer creatorId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private Set<SpaceMembership> memberships;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
