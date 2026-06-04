package com.example.riwaq.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(100)",nullable = false)
    private String name;
    @Column(columnDefinition = "varchar(255)",nullable = false,unique = true)
    private String email;
    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(255)",nullable = false,unique = true)
    private String username;

    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserBook> userBooks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;


}
