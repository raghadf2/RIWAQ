package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.OUT.FriendshipDTOOut;
import com.example.riwaq.Model.Friendship;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.FriendshipRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void acceptFriendship(Integer friendshipId, Integer userId){

        Friendship friendship = friendshipRepository.findFriendshipById(friendshipId);

        if(friendship == null){
            throw new ApiException("Friendship not found");
        }

        if(!friendship.getReceiverId().equals(userId)){
            throw new ApiException("Only receiver can accept friendship request");
        }

        if(!friendship.getStatus().equals("PENDING")){
            throw new ApiException("Only pending requests can be accepted");
        }

        friendship.setStatus("ACCEPTED");

        friendshipRepository.save(friendship);
    }

    public void rejectFriendship(Integer friendshipId, Integer userId){

        Friendship friendship = friendshipRepository.findFriendshipById(friendshipId);

        if(friendship == null){
            throw new ApiException("Friendship not found");
        }

        if(!friendship.getReceiverId().equals(userId)){
            throw new ApiException("Only receiver can reject friendship request");
        }

        if(!friendship.getStatus().equals("PENDING")){
            throw new ApiException("Only pending requests can be rejected");
        }

        friendship.setStatus("REJECTED");

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

    //==================

    public List<FriendshipDTOOut> getPendingFriendRequests(Integer userId){

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        List<Friendship> pendingFriendships = friendshipRepository.findFriendshipsByReceiverIdAndStatus(userId, "PENDING");

        List<FriendshipDTOOut> pendingFriendshipDTOs = new ArrayList<>();

        for(Friendship friendship : pendingFriendships){
            FriendshipDTOOut friendshipDTOOut = convertToDTO(friendship);
            pendingFriendshipDTOs.add(friendshipDTOOut);
        }

        return pendingFriendshipDTOs;
    }
}
