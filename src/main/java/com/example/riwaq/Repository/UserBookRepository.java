package com.example.riwaq.Repository;

import com.example.riwaq.Model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {

    UserBook findUserBookById(Integer id);
    List<UserBook> findUserBooksByUser_Id(Integer userId);

    List<UserBook> findUserBooksByUser_IdAndStatus(
            Integer userId,
            String status
    );}
