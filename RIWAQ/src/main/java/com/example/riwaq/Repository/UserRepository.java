package com.example.riwaq.Repository;

import com.example.riwaq.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById (int userId);
    User findUserByEmail(String email);

    User findUserByUsername(String username);
}
