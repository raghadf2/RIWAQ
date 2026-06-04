package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.OUT.FriendshipDTOOut;
import com.example.riwaq.Model.Friendship;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.FriendshipRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public List<FriendshipDTOOut> getAllFriendships() {
        return friendshipRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FriendshipDTOOut getFriendshipById(Integer id) {
        Friendship friendship = friendshipRepository.findFriendshipById(id);
        if (friendship == null) {
            throw new ApiException("Friendship not found");
        }
        return convertToDTO(friendship);
    }

    public void addFriendship(Integer senderId, Integer receiverId) {
        User sender = userRepository.findUserById(senderId);

        if (sender == null) {
            throw new ApiException("Sender not found");
        }

        User receiver = userRepository.findUserById(receiverId);

        if (receiver == null) {
            throw new ApiException("Receiver not found");
        }

        if (senderId.equals(receiverId)) {
            throw new ApiException("User cannot send friendship request to themselves");
        }

        Friendship existingFriendship = friendshipRepository.findFriendshipBySenderIdAndReceiverId(
                senderId,
                receiverId
        );

        if (existingFriendship != null) {
            throw new ApiException("Friendship already exists");
        }

        Friendship reverseFriendship = friendshipRepository.findFriendshipBySenderIdAndReceiverId(
                receiverId,
                senderId
        );

        if (reverseFriendship != null) {
            throw new ApiException("Friendship already exists");
        }

        Friendship friendship = new Friendship();

        friendship.setSenderId(senderId);
        friendship.setReceiverId(receiverId);
        friendship.setStatus("PENDING");

        friendshipRepository.save(friendship);
    }

    public void acceptFriendship(Integer id) {
        updateFriendshipStatus(id, "ACCEPTED");
    }

    public void rejectFriendship(Integer id) {
        updateFriendshipStatus(id, "REJECTED");
    }

    public void blockFriendship(Integer id) {
        updateFriendshipStatus(id, "BLOCKED");
    }

    private void updateFriendshipStatus(Integer id, String status) {
        Friendship friendship = friendshipRepository.findFriendshipById(id);

        if (friendship == null) {
            throw new ApiException("Friendship not found");
        }

        friendship.setStatus(status);

        friendshipRepository.save(friendship);
    }

    public void deleteFriendship(Integer id) {
        Friendship friendship = friendshipRepository.findFriendshipById(id);
        if (friendship == null) {
            throw new ApiException("Friendship not found");
        }
        friendshipRepository.delete(friendship);
    }

    private FriendshipDTOOut convertToDTO(Friendship friendship) {
        return new FriendshipDTOOut(
                friendship.getId(),
                friendship.getSenderId(),
                friendship.getReceiverId(),
                friendship.getStatus()
        );
    }
}
