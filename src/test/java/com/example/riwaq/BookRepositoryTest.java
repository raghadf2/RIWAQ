package com.example.riwaq;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void findBookByIdTesting() {

        Book book = new Book();
        book.setTitle("Atomic Habits");
        book.setAuthor("James Clear");
        book.setPageCount(300);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        bookRepository.save(book);

        Book result = bookRepository.findBookById(book.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getId(), result.getId());
    }

    @Test
    public void findBooksByAuthorTesting() {

        Book book = new Book();
        book.setTitle("Deep Work");
        book.setAuthor("Cal Newport");
        book.setPageCount(250);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        bookRepository.save(book);

        Assertions.assertEquals(1,bookRepository.findBooksByAuthor("Cal Newport").size());
    }

    @Test
    public void findBooksByCreatedByUserIdTesting() {

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setPageCount(400);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(5);

        bookRepository.save(book);

        Assertions.assertEquals(1,bookRepository.findBooksByCreatedByUserId(5).size());
    }

    @Test
    public void saveBookTesting() {

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPageCount(200);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        Book savedBook = bookRepository.save(book);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());
    }

    @Test
    public void deleteBookTesting() {

        Book book = new Book();
        book.setTitle("Delete Book");
        book.setAuthor("Author");
        book.setPageCount(100);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        Book savedBook = bookRepository.save(book);

        bookRepository.delete(savedBook);

        Book result = bookRepository.findBookById(savedBook.getId());

        Assertions.assertNull(result);
    }
}
