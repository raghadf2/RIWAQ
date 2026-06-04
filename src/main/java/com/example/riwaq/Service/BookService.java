package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.BookDto;
//import com.example.riwaq.DTO.GoogleBookDto;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.Post;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.PostRepository;
import com.example.riwaq.Repository.ReviewRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
//    private final GoogleBookService googleBookService;

    //
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final OpenAIService openAIService;

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
        book.setSource("USER_CREATED");
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
    // endpoint

    public void addBookFromGoogle(Integer userId, String title){

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        Book book = new Book();
        GoogleBookDto googleBook = googleBookService.searchBook(title);

        book.setTitle(googleBook.getTitle());
        book.setAuthor(googleBook.getAuthor());
        book.setPageCount(googleBook.getPageCount());
        book.setCreatedByUserId(user.getId());
        book.setSource("GOOGLE_BOOK");
        bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(String author){

        List<Book> books = bookRepository.findBooksByAuthor(author);

        if(books.isEmpty()){
            throw new ApiException("No books found");
        }

        return books;
    }
    public List<Book> getBooksByUserId(Integer userId){

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        List<Book> books =
                bookRepository.findBooksByCreatedByUserId(userId);

        if(books.isEmpty()){
            throw new ApiException("No books found");
        }

        return books;
    }
    public Map<String, Object> getBookDashboard(Integer bookId){

        Book book = bookRepository.findBookById(bookId);

        if(book == null){
            throw new ApiException("Book not found");
        }

        Integer postsCount = postRepository.countPostsByUserBook_Book_Id(bookId);

        List<Post> posts = postRepository.findPostsByUserBook_Book_Id(bookId);

        Integer mostPostedPage = null;
        Integer maxPosts = 0;

        for(Post post : posts){

            Integer page = post.getPageNumber();

            if(page == null){
                continue;
            }

            int count = 0;

            for(Post p : posts){
                if(p.getPageNumber() != null && p.getPageNumber().equals(page)){
                    count++;
                }
            }

            if(count > maxPosts){
                maxPosts = count;
                mostPostedPage = page;
            }
        }

//        String prompt =
//                "Return ONLY valid JSON in this format: "
//                        + "{ \"activitySummary\":\"\", \"recommendation\":\"\" }. "
//                        + "Do not return markdown. "
//                        + "Book dashboard: "
//                        + "Title = " + book.getTitle()
//                        + ", Author = " + book.getAuthor()
//                        + ", Page count = " + book.getPageCount()
//                        + ", Source = " + book.getSource()
//                        + ", Posts count = " + postsCount
//                        + ", Most posted page = " + mostPostedPage + ".";
        String prompt =
                "أنت محلل كتب عربي. "
                        + "أجب باللغة العربية فقط. "
                        + "أعد JSON صحيح فقط بهذا الشكل: "
                        + "{ \"activitySummary\":\"\", \"recommendation\":\"\" }. "
                        + "لا تضف markdown ولا ```json. "
                        + "بيانات الكتاب: "
                        + "العنوان = " + book.getTitle()
                        + ", المؤلف = " + book.getAuthor()
                        + ", عدد الصفحات = " + book.getPageCount()
                        + ", المصدر = " + book.getSource()
                        + ", عدد المنشورات = " + postsCount
                        + ", أكثر صفحة تمت مناقشتها = " + mostPostedPage
                        + ". "
                        + "اكتب ملخصًا قصيرًا عن تفاعل القراء مع الكتاب "
                        + "وقدّم توصية واحدة مفيدة.";

        Map<String, String> aiAnalysis =
                openAIService.generateJsonAnalysis(prompt);

        Map<String, Object> response = new HashMap<>();

        response.put("title", book.getTitle());
        response.put("author", book.getAuthor());
        response.put("pageCount", book.getPageCount());
        response.put("source", book.getSource());
        response.put("postsCount", postsCount);
        response.put("mostPostedPage", mostPostedPage);
        response.put("aiAnalysis", aiAnalysis);

        return response;
    }

}