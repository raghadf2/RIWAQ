package com.example.riwaq;

import com.example.riwaq.DTO.BookDto;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.PostRepository;
import com.example.riwaq.Repository.ReviewRepository;
import com.example.riwaq.Repository.UserRepository;
import com.example.riwaq.Service.BookService;
import com.example.riwaq.Service.GoogleBookService;
import com.example.riwaq.Service.OpenAIService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    OpenAIService openAIService;

    @Mock
    GoogleBookService googleBookService;

    @InjectMocks
    BookService bookService;



    @Test
    public void addBookTesting() {
        User user = new User();
        user.setId(1);

        BookDto dto = new BookDto();
        dto.setTitle("Atomic Habits");
        dto.setAuthor("James Clear");
        dto.setPageCount(300);

        when(userRepository.findUserById(1)).thenReturn(user);

        bookService.addBook(1, dto);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void getAllBooksTesting() {

        List<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId(1);
        book.setTitle("Atomic Habits");
        book.setAuthor("James Clear");
        book.setPageCount(300);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        Assertions.assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void getBooksByAuthorTesting() {

        List<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId(1);
        book.setTitle("Deep Work");
        book.setAuthor("Cal Newport");
        book.setPageCount(250);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        books.add(book);

        when(bookRepository.findBooksByAuthor("Cal Newport")).thenReturn(books);

        List<Book> result = bookService.getBooksByAuthor("Cal Newport");

        Assertions.assertEquals(1, result.size());
        verify(bookRepository, times(1)).findBooksByAuthor("Cal Newport");
    }

    @Test
    public void getBooksByUserIdTesting() {

        User user = new User();
        user.setId(1);

        List<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId(1);
        book.setTitle("Atomic Habits");
        book.setAuthor("James Clear");
        book.setPageCount(300);
        book.setSource("USER_CREATED");
        book.setCreatedByUserId(1);

        books.add(book);

        when(userRepository.findUserById(1)).thenReturn(user);
        when(bookRepository.findBooksByCreatedByUserId(1)).thenReturn(books);

        List<Book> result = bookService.getBooksByUserId(1);

        Assertions.assertEquals(1, result.size());

        verify(userRepository, times(1)).findUserById(1);
        verify(bookRepository, times(1)).findBooksByCreatedByUserId(1);
    }

    @Test
    public void deleteBookTesting() {

        Book book = new Book();
        book.setId(1);

        when(bookRepository.findBookById(1)).thenReturn(book);

        bookService.deleteBook(1);

        verify(bookRepository, times(1)).findBookById(1);
        verify(bookRepository, times(1)).delete(book);
    }
}
