package com.example.riwaq.Repository;

import com.example.riwaq.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
 Book findBookById(int bookId);

 List<Book> findBooksByAuthor(String author);

 List<Book> findBooksByCreatedByUserId(Integer userId);

 Book findBookById(Integer id);
}
