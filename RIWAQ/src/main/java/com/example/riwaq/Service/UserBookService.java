package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.In.UserBookDtoIn;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.User;
import com.example.riwaq.Model.UserBook;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.UserBookRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public void addUserBook(Integer userId, Integer bookId, UserBookDtoIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Book book = bookRepository.findBookById(bookId);
        if (book == null) {
            throw new ApiException("Book not found");
        }

        if (dto.getCurrentPage() > book.getPageCount()) {
            throw new ApiException("Current page cannot be greater than page count");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setCurrentPage(dto.getCurrentPage());
        userBook.setStatus(getStatus(dto.getCurrentPage(), book.getPageCount()));

        userBookRepository.save(userBook);
    }

    public List<UserBook> getAllUserBooks() {
        return userBookRepository.findAll();
    }

    public void updateProgress(Integer userBookId, UserBookDtoIn dto) {

        UserBook userBook = userBookRepository.findUserBookById(userBookId);
        if (userBook == null) {
            throw new ApiException("UserBook not found");
        }

        Book book = userBook.getBook();

        if (dto.getCurrentPage() > book.getPageCount()) {
            throw new ApiException("Current page cannot be greater than page count");
        }

        userBook.setCurrentPage(dto.getCurrentPage());
        userBook.setStatus(getStatus(dto.getCurrentPage(), book.getPageCount()));

        userBookRepository.save(userBook);
    }

    public void deleteUserBook(Integer userBookId) {

        UserBook userBook = userBookRepository.findUserBookById(userBookId);
        if (userBook == null) {
            throw new ApiException("UserBook not found");
        }

        userBookRepository.delete(userBook);
    }

    private String getStatus(Integer currentPage, Integer pageCount) {

        if (currentPage == 0) {
            return "NOT_STARTED";
        }

        if (currentPage >= pageCount) {
            return "COMPLETED";
        }

        return "READING";
    }
}