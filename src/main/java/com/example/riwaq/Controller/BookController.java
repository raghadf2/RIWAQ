package com.example.riwaq.Controller;

import com.example.riwaq.Api.ApiResponse;
import com.example.riwaq.DTO.BookDto;
import com.example.riwaq.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/add/{userId}")
    public ResponseEntity addBook(@PathVariable Integer userId,
                                  @RequestBody @Valid BookDto dto) {
        bookService.addBook(userId, dto);
        return ResponseEntity.status(201).body(new ApiResponse("Book added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity getAllBooks() {
        return ResponseEntity.status(200).body(bookService.getAllBooks());
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@PathVariable Integer bookId,
                                     @RequestBody @Valid BookDto dto) {
        bookService.updateBook(bookId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Book updated successfully"));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(200).body(new ApiResponse("Book deleted successfully"));
    }

    //
    @GetMapping("/user/{userId}")
    public ResponseEntity getBooksByUserId(@PathVariable Integer userId){

        return ResponseEntity.status(200)
                .body(bookService.getBooksByUserId(userId));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity getBooksByAuthor(@PathVariable String author){

        return ResponseEntity.status(200)
                .body(bookService.getBooksByAuthor(author));
    }

    @GetMapping("/filter/top-rated")
    public ResponseEntity getTopRatedBooks(){

        return ResponseEntity.status(200)
                .body(bookService.getTopRatedBooks());
    }

    @PostMapping("/add-google/{userId}")
    public ResponseEntity addBookFromGoogle(@PathVariable Integer userId,
                                            @RequestParam String title) {

        bookService.addBookFromGoogle(userId, title);

        return ResponseEntity.status(201)
                .body(new ApiResponse("Book added from Google successfully"));
    }

    @GetMapping("/dashboard/{bookId}")
    public ResponseEntity getBookDashboard(@PathVariable Integer bookId){

        return ResponseEntity.status(200)
                .body(bookService.getBookDashboard(bookId));
    }

    @GetMapping("/similar/{bookId}")
    public ResponseEntity getSimilarBooks(@PathVariable Integer bookId) {

        return ResponseEntity.status(200)
                .body(bookService.getSimilarBooks(bookId));
    }
}
