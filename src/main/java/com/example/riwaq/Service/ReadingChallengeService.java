package com.example.riwaq.Service;

import com.example.riwaq.DTO.IN.ReadingChallengeDTOIn;
import com.example.riwaq.Model.Book;
import com.example.riwaq.Model.Friendship;
import com.example.riwaq.Model.ReadingChallenge;
import com.example.riwaq.Repository.BookRepository;
import com.example.riwaq.Repository.FriendshipRepository;
import com.example.riwaq.Repository.ReadingChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingChallengeService {

    private final ReadingChallengeRepository readingChallengeRepository;
    private final FriendshipRepository friendshipRepository;
    private final BookRepository bookRepository;

    public void createChallenge(ReadingChallengeDTOIn dto) {

        Friendship friendship = friendshipRepository.findFriendshipById(dto.getFriendshipId());

        if (friendship == null) {
            throw new RuntimeException("Friendship not found");
        }

        Book book = bookRepository.findBookById(dto.getBookId());

        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        ReadingChallenge challenge = new ReadingChallenge();

        challenge.setFriendshipId(dto.getFriendshipId());
        challenge.setBookId(dto.getBookId());
        challenge.setSenderId(dto.getSenderId());
        challenge.setReceiverId(dto.getReceiverId());
        challenge.setSenderPage(dto.getSenderPage());
        challenge.setReceiverPage(dto.getReceiverPage());

        challenge.setStatus("PENDING");
        challenge.setCreatedAt(new Date());

        readingChallengeRepository.save(challenge);
    }

    public List<ReadingChallenge> getAllChallenges() {
        return readingChallengeRepository.findAll();
    }

    public void updateChallenge(Integer id, ReadingChallengeDTOIn dto) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(id);

        if (challenge == null) {
            throw new RuntimeException("Not found");
        }

        challenge.setSenderPage(dto.getSenderPage());
        challenge.setReceiverPage(dto.getReceiverPage());

        if (challenge.getStatus().equals("PENDING")) {
            challenge.setStatus("IN_PROGRESS");
            challenge.setRespondedAt(new Date());
        }

        if (challenge.getSenderPage() >= 50 && challenge.getReceiverPage() >= 50) {
            challenge.setStatus("COMPLETED");
            challenge.setCompletedAt(new Date());
        }

        readingChallengeRepository.save(challenge);
    }

    public void deleteChallenge(Integer id) {

        ReadingChallenge challenge = readingChallengeRepository.findReadingChallengeById(id);

        if (challenge == null) {
            throw new RuntimeException("Challenge not found");
        }

        readingChallengeRepository.delete(challenge);
    }
}
