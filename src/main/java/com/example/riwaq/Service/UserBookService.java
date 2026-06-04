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

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OpenAIService  openAIService;
    private final NotificationService notificationService;

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
        notificationService.sendBookAddedNotification(
                user.getId(),
                book.getTitle()
        );    }

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

        notificationService.sendBookAddedNotification(
                userBook.getUser().getId(),
                book.getTitle()
        );
        if (dto.getCurrentPage() == book.getPageCount()) {
            notificationService.sendBookCompletedNotification(  userBook.getUser().getId(),
                    book.getTitle());
        }

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

    // endpoint

    public List<UserBook> getBooksByStatus(Integer userId, String status){

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        List<UserBook> books =
                userBookRepository.findUserBooksByUser_IdAndStatus(userId,status);

        if(books.isEmpty()){
            throw new ApiException("No books found");
        }

        return books;
    }

    public Map<String, Object> getDashboard(Integer userId){
        List<UserBook> userBooks =
                userBookRepository.findUserBooksByUser_Id(userId);

        int completed = 0;
        int reading = 0;
        int notStarted = 0;

        long longestDays = 0;
        String longestBook = "No completed books yet";

        for(UserBook userBook : userBooks){

            if(userBook.getStatus().equals("COMPLETED")){
                completed++;
            }

            else if(userBook.getStatus().equals("READING")){
                reading++;
            }

            else{
                notStarted++;
            }

            if(userBook.getStartedAt() != null && userBook.getFinishedAt() != null){

                long days = ChronoUnit.DAYS.between(
                        userBook.getStartedAt(),
                        userBook.getFinishedAt()
                );

                if(days > longestDays){
                    longestDays = days;
                    longestBook = userBook.getBook().getTitle();
                }
            }
        }

//        String prompt =
//                "Return ONLY valid JSON in this format: "
//                        + "{ \"readerProfile\":\"\", \"readingAdvice\":\"\" }. "
//                        + "Do not return markdown. "
//                        + "Reader statistics: "
//                        + "Total books = " + userBooks.size()
//                        + ", Completed = " + completed
//                        + ", Reading = " + reading
//                        + ", Not started = " + notStarted
//                        + ", Longest reading duration book = " + longestBook
//                        + " for " + longestDays + " days.";
        String prompt =
                "أنت مستشار قراءة عربي. "
                        + "أجب باللغة العربية فقط. "
                        + "أعد JSON صحيح فقط بهذا الشكل: "
                        + "{ \"readerProfile\":\"\", \"readingAdvice\":\"\" }. "
                        + "لا تضف markdown ولا ```json. "
                        + "إحصائيات القارئ: "
                        + "إجمالي الكتب = " + userBooks.size()
                        + ", الكتب المكتملة = " + completed
                        + ", الكتب الجاري قراءتها = " + reading
                        + ", الكتب التي لم تبدأ = " + notStarted
                        + ", أطول مدة قراءة كانت لكتاب "
                        + longestBook
                        + " لمدة "
                        + longestDays
                        + " يومًا. "
                        + "اكتب وصفًا قصيرًا للقارئ ونصيحة واحدة لتحسين عاداته القرائية.";

        Map<String, String> aiAnalysis =
                openAIService.generateJsonAnalysis(prompt);

        Map<String, Object> response = new HashMap<>();

        response.put("totalBooks", userBooks.size());
        response.put("completed", completed);
        response.put("reading", reading);
        response.put("notStarted", notStarted);
        response.put("longestBook", longestBook);
        response.put("longestDays", longestDays);
        response.put("aiAnalysis", aiAnalysis);

        return response;
    }
}