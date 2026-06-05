package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.In.ReadingChallengeDTOIn;
import com.example.riwaq.DTO.Out.ReadingChallengeDTOOut;
import com.example.riwaq.Model.*;
import com.example.riwaq.Repository.*;
import com.example.riwaq.Repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        Friendship friendship = friendshipRepository.findFriendshipBySenderIdAndReceiverId(senderId, receiverId);

        if (friendship == null) {
            friendship = friendshipRepository.findFriendshipBySenderIdAndReceiverId(receiverId, senderId);
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

        UserBook receiverBook = userBookRepository.findUserBookByUser_IdAndBook_Id(receiverId, bookId);

        if (receiverBook == null) {
            throw new ApiException("Receiver must add the book before joining challenge");
        }

        if (senderBook.getStatus().equalsIgnoreCase("NOT_STARTED")) {
            throw new ApiException("Sender must start reading the book before creating challenge");
        }

        if (receiverBook.getStatus().equalsIgnoreCase("NOT_STARTED")) {
            throw new ApiException("Receiver must start reading the book before joining challenge");
        }

        validatePages(dto, book);

        ReadingChallenge existing1 = readingChallengeRepository.findReadingChallengeByBookIdAndSenderIdAndReceiverIdAndStatusNot(bookId, senderId, receiverId, "COMPLETED");

        ReadingChallenge existing2 = readingChallengeRepository.findReadingChallengeByBookIdAndReceiverIdAndSenderIdAndStatusNot(bookId, senderId, receiverId, "COMPLETED");

        if (existing1 != null || existing2 != null) {
            throw new ApiException("There is already an active challenge for this book between these users");
        }

        ReadingChallenge challenge = new ReadingChallenge();

        challenge.setFriendshipId(friendship.getId());
        challenge.setBookId(bookId);
        challenge.setSenderId(senderId);
        challenge.setReceiverId(receiverId);
        challenge.setSenderPage(dto.getSenderPage());
        challenge.setReceiverPage(dto.getReceiverPage());
        challenge.setStatus("PENDING");
        challenge.setCreatedAt(new Date());

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

        ReadingChallenge challenge =
                readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        return convertToDTOOut(challenge);
    }

    public void updateChallenge(Integer challengeId, ReadingChallengeDTOIn dto) {

        ReadingChallenge challenge =
                readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        if (challenge.getStatus().equalsIgnoreCase("COMPLETED")) {
            throw new ApiException("Completed challenge cannot be updated");
        }

        Book book = bookRepository.findBookById(challenge.getBookId());
        if (book == null) {
            throw new ApiException("Book not found");
        }

        validatePages(dto, book);

        challenge.setSenderPage(dto.getSenderPage());
        challenge.setReceiverPage(dto.getReceiverPage());

        if (dto.getSenderPage().equals(book.getPageCount()) && dto.getReceiverPage().equals(book.getPageCount())) {

            challenge.setStatus("COMPLETED");
            challenge.setCompletedAt(new Date());

        } else {
            challenge.setStatus("IN_PROGRESS");

            if (challenge.getRespondedAt() == null) {
                challenge.setRespondedAt(new Date());
            }
        }

        readingChallengeRepository.save(challenge);
    }

    public void deleteChallenge(Integer challengeId, Integer requesterId) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(challengeId);

        if (challenge == null) {
            throw new ApiException("Challenge not found");
        }

        if (!challenge.getSenderId().equals(requesterId)) {
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
        dtoOut.setFriendshipId(challenge.getFriendshipId());
        dtoOut.setBookId(challenge.getBookId());
        dtoOut.setSenderId(challenge.getSenderId());
        dtoOut.setReceiverId(challenge.getReceiverId());
        dtoOut.setSenderPage(challenge.getSenderPage());
        dtoOut.setReceiverPage(challenge.getReceiverPage());
        dtoOut.setStatus(challenge.getStatus());
        dtoOut.setCreatedAt(challenge.getCreatedAt());
        dtoOut.setRespondedAt(challenge.getRespondedAt());
        dtoOut.setCompletedAt(challenge.getCompletedAt());

        return dtoOut;
    }

    //===============

    public List<ReadingChallengeDTOOut> getChallengesByStatus(String status) {

        if (!status.equalsIgnoreCase("PENDING") &&
                !status.equalsIgnoreCase("IN_PROGRESS") &&
                !status.equalsIgnoreCase("COMPLETED")) {
            throw new ApiException("Status must be PENDING, IN_PROGRESS, or COMPLETED");
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

    public List<ReadingChallengeDTOOut> getChallengesByDate(Date startDate, Date endDate) {

        List<ReadingChallenge> challenges =
                readingChallengeRepository.findReadingChallengesByCreatedAtBetween(startDate, endDate);

        if (challenges.isEmpty()) {
            throw new ApiException("No challenges found in this date range");
        }

        List<ReadingChallengeDTOOut> dtoOutList = new ArrayList<>();

        for (ReadingChallenge challenge : challenges) {
            dtoOutList.add(convertToDTOOut(challenge));
        }

        return dtoOutList;
    }
}
