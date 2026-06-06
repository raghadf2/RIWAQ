package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.In.SpaceDTOIn;
import com.example.riwaq.DTO.Out.SpaceDTOOut;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.Space;
import com.example.riwaq.Model.User;
import com.example.riwaq.Model.UserBook;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.SpaceRepository;
import com.example.riwaq.Repository.UserBookRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final OpenAIService openAIService;

    public void addSpace(Integer bookId, Integer creatorId, SpaceDTOIn dto) {
        Book book = bookRepository.findBookById(bookId);

        if (book == null) {
            throw new ApiException("Book not found");
        }

        User creator = userRepository.findUserById(creatorId);

        if (creator == null) {
            throw new ApiException("User not found");
        }

        UserBook userBook = userBookRepository.findUserBookByUserIdAndBookId(creatorId, bookId);

        if (userBook == null) {
            throw new ApiException("User must add the book before creating a space");
        }

        if (!userBook.getStatus().equalsIgnoreCase("COMPLETED")) {
            throw new ApiException("User must complete the book before creating a space");
        }

        Space existing = spaceRepository.findSpaceByBookIdAndName(bookId, dto.getName());

        if (existing != null) {
            throw new ApiException("Space with same name already exists for this book");
        }

        Space space = new Space();
        space.setBookId(bookId);
        space.setCreatorId(creatorId);
        space.setName(dto.getName());
        space.setDescription(dto.getDescription());

        spaceRepository.save(space);
    }

    public List<SpaceDTOOut> getAllSpaces() {

        List<Space> spaces = spaceRepository.findAll();
        List<SpaceDTOOut> dtoOutList = new ArrayList<>();

        for (Space space : spaces) {
            SpaceDTOOut dtoOut = new SpaceDTOOut();

            dtoOut.setSpaceId(space.getSpaceId());
            dtoOut.setBookId(space.getBookId());
            dtoOut.setCreatorId(space.getCreatorId());
            dtoOut.setName(space.getName());
            dtoOut.setDescription(space.getDescription());

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }

    public void updateSpace(Integer spaceId, SpaceDTOIn dto) {
        Space space = spaceRepository.findSpaceBySpaceId(spaceId);

        if (space == null) {
            throw new ApiException("Space not found");
        }

        if (space.getMemberships() != null && !space.getMemberships().isEmpty()) {
            throw new ApiException("Cannot update space, it has active members");
        }

        Space existing = spaceRepository.findSpaceByBookIdAndName(space.getBookId(), dto.getName());

        if (existing != null && !existing.getSpaceId().equals(spaceId)) {
            throw new ApiException("Space with same name already exists for this book");
        }

        space.setName(dto.getName());
        space.setDescription(dto.getDescription());

        spaceRepository.save(space);
    }

    public void deleteSpace(Integer spaceId, Integer requesterId) {

        Space space = spaceRepository.findSpaceBySpaceId(spaceId);
        if (space == null) {
            throw new ApiException("Space not found");
        }

        User requester = userRepository.findUserById(requesterId);
        if (requester == null) {
            throw new ApiException("Requester not found");
        }

        if (!space.getCreatorId().equals(requesterId)) {
            throw new ApiException("Only space creator can delete the space");
        }

        if (space.getMemberships() != null && !space.getMemberships().isEmpty()) {
            throw new ApiException("Cannot delete space with active members");
        }

        spaceRepository.delete(space);
    }

    //===============

    public Object suggestSpaceQuestions(Integer bookId) {

        Book book = bookRepository.findBookById(bookId);

        if (book == null) {
            throw new ApiException("Book not found");
        }

        String prompt =
                "أنت مساعد لنظام قراءة اجتماعي. " +
                        "اقترح 5 أسئلة نقاش عن كتاب: " + book.getTitle() + " للمؤلف " + book.getAuthor() + ". " +
                        "لا تجعل questions فارغة. " +
                        "أعد JSON فقط بهذا الشكل بالضبط: " +
                        "{ \"bookTitle\":\"" + book.getTitle() + "\", " +
                        "\"questions\":\"سؤال 1 | سؤال 2 | سؤال 3 | سؤال 4 | سؤال 5\" }";

        return openAIService.generateJsonAnalysis(prompt);
    }


    public List<SpaceDTOOut> getSpacesByUserBookStatus(Integer userId, String status) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!status.equalsIgnoreCase("NOT_STARTED") &&
                !status.equalsIgnoreCase("READING") &&
                !status.equalsIgnoreCase("COMPLETED")) {
            throw new ApiException("Status must be NOT_STARTED, READING, or COMPLETED");
        }

        List<Space> spaces = spaceRepository.findAll();
        List<SpaceDTOOut> result = new ArrayList<>();

        for (Space space : spaces) {

            UserBook userBook = userBookRepository.findUserBookByUserIdAndBookId(userId, space.getBookId());

            if (userBook != null && userBook.getStatus().equalsIgnoreCase(status)) {

                SpaceDTOOut dtoOut = new SpaceDTOOut();

                dtoOut.setSpaceId(space.getSpaceId());
                dtoOut.setBookId(space.getBookId());
                dtoOut.setCreatorId(space.getCreatorId());
                dtoOut.setName(space.getName());
                dtoOut.setDescription(space.getDescription());

                result.add(dtoOut);
            }
        }

        if (result.isEmpty()) {
            throw new ApiException("No spaces found for this status");
        }

        return result;
    }

    public Object generateReflectionPrompts(Integer bookId, Integer pageNumber) {

        Book book = bookRepository.findBookById(bookId);

        if (book == null) {
            throw new ApiException("Book not found");
        }

        if (pageNumber < 1 || pageNumber > book.getPageCount()) {
            throw new ApiException("Page number must be between 1 and " + book.getPageCount());
        }

        String prompt =
                "أنت مساعد لمنصة قراءة اجتماعية. " +
                        "أنشئ 5 أسئلة تأمل شخصية للقارئ حول الصفحة رقم " + pageNumber +
                        " من كتاب " + book.getTitle() +
                        " للمؤلف " + book.getAuthor() + ". " +
                        "لا تقتبس من محتوى الكتاب. " +
                        "أعد JSON فقط بدون markdown وبدون شرح بهذا الشكل بالضبط: " +
                        "{ \"bookTitle\":\"" + book.getTitle() + "\", " +
                        "\"reflectionPrompts\":\"سؤال 1 | سؤال 2 | سؤال 3 | سؤال 4 | سؤال 5\" }";
        return openAIService.generateJsonAnalysis(prompt);
    }
}
