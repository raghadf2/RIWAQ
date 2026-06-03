package com.example.riwaq.Repository;

import com.example.riwaq.Model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {

    List<UserBook> findAllByUserId(Integer userId);
    UserBook findUserBookById(Integer id);

}
