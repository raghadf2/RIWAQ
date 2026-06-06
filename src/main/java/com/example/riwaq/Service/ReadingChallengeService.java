package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.ReadingChallengeDTOIn;
import com.example.riwaq.DTO.OUT.ReadingChallengeDTOOut;
import com.example.riwaq.Model.*;
import com.example.riwaq.Repository.*;
import com.example.riwaq.Repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingChallengeService {

    private final ReadingChallengeRepository readingChallengeRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final FriendshipRepository friendshipRepository;
    private final NotificationService notificationService;
    private final WhatsAppService whatsAppService;

    public void addChallenge(Integer bookId, Integer senderId, Integer receiverId, ReadingChallengeDTOIn dto) {
        Book book = bookRepository.findBookById(bookId);

        if (book == null) {
            throw new ApiException("Book not found");
        }

        User sender = userRepository.findUserById(senderId);

        if (sender == null) {
            throw new ApiException("Sender not found");
        }

        User receiver = userRepository.findUserById(receiverId);
        if (receiver == null) {
            throw new ApiException("Receiver not found");
        }

        if (senderId.equals(receiverId)) {
            throw new ApiException("User cannot challenge themselves");
        }

        Friendship friendship = friendshipRepository.findFriendshipBySender_IdAndReceiver_Id(senderId, receiverId);

        if (friendship == null) {
            friendship = friendshipRepository.findFriendshipBySender_IdAndReceiver_Id(receiverId, senderId);
        }

        if (friendship == null) {
            throw new ApiException("Users are not friends");
        }

        if (!friendship.getStatus().equalsIgnoreCase("ACCEPTED")) {
            throw new ApiException("Friendship must be accepted before creating challenge");
        }

        UserBook senderBook = userBookRepository.findUserBookByUser_IdAndBook_Id(senderId, bookId);

        if (senderBook == null) {
            throw new ApiException("Sender must add the book before creating challenge");
        }

        if (senderBook.getStatus().equalsIgnoreCase("NOT_STARTED")) {
            throw new ApiException("Sender must start reading the book before creating challenge");
        }

        validatePages(dto, book);

        ReadingChallenge existing1 = readingChallengeRepository.findReadingChallengeByBookIdAndSenderIdAndReceiverIdAndStatusNot(bookId, senderId, receiverId, "COMPLETED");

        ReadingChallenge existing2 = readingChallengeRepository.findReadingChallengeByBookIdAndReceiverIdAndSenderIdAndStatusNot(bookId, senderId, receiverId, "COMPLETED");

        if (existing1 != null || existing2 != null) {
            throw new ApiException("There is already an active challenge for this book between these users");
        }

        ReadingChallenge challenge = new ReadingChallenge();

        challenge.setFriendship(friendship);
        challenge.setBook(book);
        challenge.setSenderPage(dto.getSenderPage());
        challenge.setReceiverPage(dto.getReceiverPage());
        challenge.setStatus("PENDING");
        challenge.setCreatedAt(LocalDateTime.now());

        readingChallengeRepository.save(challenge);
    }

    public List<ReadingChallengeDTOOut> getAllChallenges() {

        List<ReadingChallenge> challenges = readingChallengeRepository.findAll();
        List<ReadingChallengeDTOOut> dtoOutList = new ArrayList<>();

        for (ReadingChallenge challenge : challenges) {
            dtoOutList.add(convertToDTOOut(challenge));
        }

        return dtoOutList;
    }

    public ReadingChallengeDTOOut getChallengeById(Integer challengeId) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        return convertToDTOOut(challenge);
    }

    public void deleteChallenge(Integer challengeId, Integer requesterId) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        Integer senderId = challenge.getFriendship().getSender().getId();

        if (!senderId.equals(requesterId)) {
            throw new ApiException("Only challenge sender can delete this challenge");
        }

        readingChallengeRepository.delete(challenge);
    }

    private void validatePages(ReadingChallengeDTOIn dto, Book book) {

        if (dto.getSenderPage() > book.getPageCount()) {
            throw new ApiException("Sender page cannot exceed book page count");
        }

        if (dto.getReceiverPage() > book.getPageCount()) {
            throw new ApiException("Receiver page cannot exceed book page count");
        }
    }

    private ReadingChallengeDTOOut convertToDTOOut(ReadingChallenge challenge) {

        ReadingChallengeDTOOut dtoOut = new ReadingChallengeDTOOut();

        dtoOut.setId(challenge.getId());
        dtoOut.setFriendshipId(challenge.getFriendship().getId());
        dtoOut.setBookId(challenge.getBook().getId());
        dtoOut.setSenderId(challenge.getFriendship().getSender().getId());
        dtoOut.setReceiverId(challenge.getFriendship().getReceiver().getId());
        dtoOut.setSenderPage(challenge.getSenderPage());
        dtoOut.setReceiverPage(challenge.getReceiverPage());
        dtoOut.setStatus(challenge.getStatus());
        dtoOut.setCreatedAt(challenge.getCreatedAt());
        dtoOut.setRespondedAt(challenge.getRespondedAt());
        dtoOut.setCompletedAt(challenge.getCompletedAt());

        if (challenge.getWinner() != null) {
            dtoOut.setWinnerId(challenge.getWinner().getId());
        }

        return dtoOut;
    }

    //===============

    public void acceptChallenge(Integer challengeId, Integer receiverId) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        Integer challengeReceiverId = challenge.getFriendship().getReceiver().getId();

        if (!challengeReceiverId.equals(receiverId)) {
            throw new ApiException("Only challenge receiver can accept this challenge");
        }

        if (!challenge.getStatus().equalsIgnoreCase("PENDING")) {
            throw new ApiException("Only pending challenges can be accepted");
        }

        User receiver = userRepository.findUserById(receiverId);

        if (receiver == null) {
            throw new ApiException("Receiver not found");
        }

        Book book = challenge.getBook();

        if (book == null) {
            throw new ApiException("Book not found");
        }

        UserBook receiverBook = userBookRepository.findUserBookByUser_IdAndBook_Id(receiverId, book.getId());

        if (receiverBook == null) {

            receiverBook = new UserBook();
            receiverBook.setUser(receiver);
            receiverBook.setBook(book);
            receiverBook.setCurrentPage(0);
            receiverBook.setProgressPercentage(0);
            receiverBook.setStatus("READING");

            userBookRepository.save(receiverBook);

        } else if (receiverBook.getStatus().equalsIgnoreCase("NOT_STARTED")) {

            receiverBook.setStatus("READING");
            receiverBook.setCurrentPage(0);
            receiverBook.setProgressPercentage(0);

            userBookRepository.save(receiverBook);
        }

        challenge.setStatus("IN_PROGRESS");

        readingChallengeRepository.save(challenge);

        notificationService.sendChallengeAcceptedNotification(
                challenge.getFriendship().getSender().getId(),
                challenge.getId(),
                receiverId,
                book.getTitle()
        );
    }

    public void rejectChallenge(Integer challengeId, Integer userId) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        Integer receiverId = challenge.getFriendship().getReceiver().getId();

        if (!receiverId.equals(userId)) {
            throw new ApiException("Only challenge receiver can reject this challenge");
        }

        if (!challenge.getStatus().equalsIgnoreCase("PENDING")) {
            throw new ApiException("Only pending challenges can be rejected");
        }

        challenge.setStatus("REJECTED");
        challenge.setRespondedAt(LocalDateTime.now());

        readingChallengeRepository.save(challenge);
    }

    public void updateMyProgress(Integer challengeId, Integer userId, Integer page) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        User sender = challenge.getFriendship().getSender();
        User receiver = challenge.getFriendship().getReceiver();
        Book book = challenge.getBook();

        Integer senderId = sender.getId();
        Integer receiverId = receiver.getId();

        if (!senderId.equals(userId) && !receiverId.equals(userId)) {
            throw new ApiException("Only users inside this challenge can update progress");
        }

        if (!challenge.getStatus().equalsIgnoreCase("IN_PROGRESS")) {
            throw new ApiException("Only in progress challenges can be updated");
        }

        if (page < 0) {
            throw new ApiException("Page cannot be negative");
        }

        if (page > book.getPageCount()) {
            throw new ApiException("Page cannot exceed book page count");
        }

        UserBook userBook = userBookRepository.findUserBookByUser_IdAndBook_Id(userId, book.getId());

        if (userBook == null) {
            throw new ApiException("User book not found");
        }

        Integer otherUserId;

        if (senderId.equals(userId)) {

            if (page < challenge.getSenderPage()) {
                throw new ApiException("Sender page cannot go backwards");
            }

            challenge.setSenderPage(page);
            otherUserId = receiverId;

        } else {

            if (page < challenge.getReceiverPage()) {
                throw new ApiException("Receiver page cannot go backwards");
            }

            challenge.setReceiverPage(page);
            otherUserId = senderId;
        }

        userBook.setCurrentPage(page);
        userBook.setProgressPercentage((page * 100) / book.getPageCount());

        if (page.equals(book.getPageCount())) {
            userBook.setStatus("COMPLETED");
        } else {
            userBook.setStatus("READING");
        }

        userBookRepository.save(userBook);

        updateReadingStreak(userBook);

        notificationService.sendChallengeProgressNotification(
                otherUserId,
                challenge.getId(),
                userId,
                book.getTitle(),
                page,
                book.getPageCount()
        );

        if (page.equals(book.getPageCount())) {

            User winner = userRepository.findUserById(userId);

            if (winner == null) {
                throw new ApiException("Winner not found");
            }

            challenge.setWinner(winner);
            challenge.setStatus("COMPLETED");
            challenge.setRespondedAt(LocalDateTime.now());

            notificationService.sendChallengeWinnerNotification(
                    senderId,
                    challenge.getId(),
                    userId,
                    book.getTitle()
            );

            notificationService.sendChallengeWinnerNotification(
                    receiverId,
                    challenge.getId(),
                    userId,
                    book.getTitle()
            );

            String whatsappMessage =
                    " Reading Challenge Finished!\n\n" +
                            " " + book.getTitle() + "\n" +
                            " Winner: " + winner.getName() + "\n\n" +
                            "Thanks for participating in the challenge. Keep reading, stay motivated, and see you in the next one! " +
                            "\n\n— Team Riwaq";

            if (sender.getPhoneNumber() != null && !sender.getPhoneNumber().isBlank()) {
                whatsAppService.sendWhatsAppMessage(sender.getPhoneNumber(), whatsappMessage);
            }

            if (receiver.getPhoneNumber() != null && !receiver.getPhoneNumber().isBlank()) {
                whatsAppService.sendWhatsAppMessage(receiver.getPhoneNumber(), whatsappMessage);
            }
        }

        readingChallengeRepository.save(challenge);
    }

    public List<ReadingChallengeDTOOut> getChallengesByStatus(String status) {

        if (!status.equalsIgnoreCase("PENDING") &&
                !status.equalsIgnoreCase("IN_PROGRESS") &&
                !status.equalsIgnoreCase("COMPLETED") &&
               !status.equalsIgnoreCase("REJECTED")){
            throw new ApiException("Status must be PENDING, IN_PROGRESS,COMPLETED or REJECTED");
        }

        List<ReadingChallenge> challenges = readingChallengeRepository.findReadingChallengesByStatus(status.toUpperCase());

        if (challenges.isEmpty()) {
            throw new ApiException("No challenges found with this status");
        }

        List<ReadingChallengeDTOOut> dtoOutList = new ArrayList<>();

        for (ReadingChallenge challenge : challenges) {
            dtoOutList.add(convertToDTOOut(challenge));
        }

        return dtoOutList;
    }

    public List<ReadingChallengeDTOOut> getChallengesByDate(LocalDate startDate, LocalDate endDate) {

        List<ReadingChallenge> challenges = readingChallengeRepository.findReadingChallengesByCreatedAtBetween(startDate, endDate);

        if (challenges.isEmpty()) {
            throw new ApiException("No challenges found in this date range");
        }

        List<ReadingChallengeDTOOut> dtoOutList = new ArrayList<>();

        for (ReadingChallenge challenge : challenges) {
            dtoOutList.add(convertToDTOOut(challenge));
        }

        return dtoOutList;
    }

    private void updateReadingStreak(UserBook userBook){

        LocalDate today = LocalDate.now();

        if(userBook.getLastReadingDate() == null){

            userBook.setReadingStreak(1);

        } else {

            long daysDifference = ChronoUnit.DAYS.between(userBook.getLastReadingDate(), today);

            if(daysDifference == 0){

                return;

            } else if(daysDifference == 1){

                userBook.setReadingStreak(userBook.getReadingStreak() + 1);

            } else {

                userBook.setReadingStreak(1);
            }
        }

        userBook.setLastReadingDate(today);

        userBookRepository.save(userBook);
    }
}
