package com.example.riwaq.Repository;

import com.example.riwaq.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findPostById(Integer id);
    List<Post> findPostsByUserId(Integer userId);
    List<Post> findPostsByUserBookId(Integer userBookId);

    Integer countPostsByUserBook_Book_Id(Integer bookId);
    List<Post> findPostsByUserBook_Book_Id(Integer bookId);
}
