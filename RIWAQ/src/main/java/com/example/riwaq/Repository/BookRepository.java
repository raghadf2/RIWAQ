package com.example.riwaq.Repository;

import com.example.riwaq.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
 Book findBookById(int bookId);
}
