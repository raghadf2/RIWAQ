package com.example.riwaq.Controller;

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
        return ResponseEntity.status(201).body("Book added successfully");
    }

    @GetMapping("/get")
    public ResponseEntity getAllBooks() {
        return ResponseEntity.status(200).body(bookService.getAllBooks());
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@PathVariable Integer bookId,
                                     @RequestBody @Valid BookDto dto) {
        bookService.updateBook(bookId, dto);
        return ResponseEntity.status(200).body("Book updated successfully");
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(200).body("Book deleted successfully");
    }
}
