package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.UserBookDtoIn;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.User;
import com.example.riwaq.Model.UserBook;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.UserBookRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        UserBook existingUserBook =
                userBookRepository.findUserBookByUser_IdAndBook_Id(
                        userId,
                        bookId
                );
        // Check if the user already added this book before.
        // this prevents duplicate books in the user's reading list.
        if (existingUserBook != null) {
            throw new ApiException(
                    "This book is already in the user's reading list"
            );
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

        if (userBook.getStartedAt() == null && dto.getCurrentPage() > 0) {
            userBook.setStartedAt(LocalDate.now());
        }

        if (userBook.getStatus().equals("COMPLETED") && userBook.getFinishedAt() == null) {
            userBook.setFinishedAt(LocalDate.now());
            userBook.setProgressPercentage(100);
            notificationService.sendBookCompletedNotification(
                    userBook.getUser().getId(),
                    book.getTitle()
            );
            List<String> similarBooks = getSimilarBookTitles(book);

            notificationService.sendSimilarBooksNotification(
                    userBook.getUser().getId(),
                    book.getTitle(),
                    similarBooks
            );
        }


        Integer progress =
                (dto.getCurrentPage() * 100)
                        / book.getPageCount();

        userBook.setProgressPercentage(progress);

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

        int totalProgress = 0;
        int almostCompletedBooks = 0;
        int highestProgress = 0;
        String highestProgressBook = "No books yet";

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
            Integer progress = userBook.getProgressPercentage();

            if(progress == null){
                progress = 0;
            }

            totalProgress += progress;

            if(progress >= 80 && progress < 100){
                almostCompletedBooks++;
            }

            if(progress > highestProgress){
                highestProgress = progress;
                highestProgressBook = userBook.getBook().getTitle();
            }
        }
        int averageProgress = 0;

        if(!userBooks.isEmpty()){
            averageProgress = totalProgress / userBooks.size();
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
                        + ", متوسط التقدم = " + averageProgress + "%"
                        + ", عدد الكتب القريبة من الانتهاء = " + almostCompletedBooks
                        + ", أكثر كتاب متقدم فيه القارئ = " + highestProgressBook
                        + " بنسبة " + highestProgress + "%. "
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
        response.put("almostCompletedBooks", almostCompletedBooks);
        response.put("averageProgress", averageProgress+ " %");
        response.put("highestProgressBook", highestProgressBook);
        response.put("highestProgress", highestProgress + " %");


        return response;
    }
    public List<UserBook> getBooksBetweenDates(LocalDate date1, LocalDate date2){
        List<UserBook> books =
                userBookRepository.findByStartedAtBetween(
                        date1,
                        date2
                );

        if(books.isEmpty()){
            throw new ApiException("No books found in this date range");
        }

        return books;
    }

    private List<String> getSimilarBookTitles(Book book) {

        String prompt =
                "أنت مستشار كتب عربي. "
                        + "اقترح 5 كتب مشابهة لهذا الكتاب. "
                        + "أعد JSON صحيح فقط بهذا الشكل: "
                        + "{ \"similarBooks\":\"\" }. "
                        + "داخل similarBooks اكتب أسماء الكتب فقط بالعربي. "
                        + "كل كتاب في سطر مستقل. "
                        + "استخدم \\n بين كل كتاب والذي يليه. "
                        + "لا تكتب المؤلف ولا سبب التشابه. "
                        + "لا تضف markdown ولا ```json. "
                        + "بيانات الكتاب: "
                        + "العنوان = " + book.getTitle()
                        + ", المؤلف = " + book.getAuthor()
                        + ".";
        Map<String, String> aiResponse =
                openAIService.generateJsonAnalysis(prompt);

        String suggestions = aiResponse.get("similarBooks");

        return List.of(suggestions.split("\n"));
    }
    public List<UserBook> getAlmostCompletedBooks(Integer userId){

        List<UserBook> books =
                userBookRepository
                        .findUserBooksByUser_IdAndProgressPercentageGreaterThanEqualAndProgressPercentageLessThan(
                                userId,
                                80,
                                100
                        );

        if(books.isEmpty()){
            throw new ApiException("No almost completed books found");
        }

        return books;
    }
}