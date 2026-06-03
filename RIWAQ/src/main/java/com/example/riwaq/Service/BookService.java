package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.BookDto;
import com.example.riwaq.DTO.GoogleBookDto;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final GoogleBookService googleBookService;

    public void addBook(Integer userId, BookDto dto) {

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPageCount(dto.getPageCount());
        book.setCreatedByUserId(user.getId());

        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void updateBook(Integer bookId, BookDto dto) {

        Book book = bookRepository.findBookById(bookId);

        if(book == null){
            throw new ApiException("Book not found");
        }

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPageCount(dto.getPageCount());

        bookRepository.save(book);
    }

    public void deleteBook(Integer bookId) {

        Book book = bookRepository.findBookById(bookId);

        if(book == null){
            throw new ApiException("Book not found");
        }

        bookRepository.delete(book);
    }
//    public void addBookFromGoogle(Integer userId, String title){
//
//        User user = userRepository.findUserById(userId);
//
//        if(user == null){
//            throw new ApiException("User not found");
//        }
//
//        GoogleBookDto googleBook =
//                googleBookService.searchBook(title);
//
//        Book book = new Book();
//
//        book.setTitle(googleBook.getTitle());
//        book.setAuthor(googleBook.getAuthor());
//        book.setPageCount(googleBook.getPageCount());
//        book.setCreatedByUserId(user.getId());
//
//        bookRepository.save(book);
//    }
}