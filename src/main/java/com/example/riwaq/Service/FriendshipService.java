package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.FriendshipDTOIn;
import com.example.riwaq.DTO.OUT.FriendshipDTOOut;
import com.example.riwaq.Model.Friendship;
import com.example.riwaq.Repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

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

    public void addFriendship(FriendshipDTOIn dto) {
        Friendship friendship = new Friendship();
        friendship.setSenderId(dto.getSenderId());
        friendship.setReceiverId(dto.getReceiverId());
        friendship.setStatus(dto.getStatus());
        friendshipRepository.save(friendship);
    }

    public void updateFriendship(Integer id, FriendshipDTOIn dto) {
        Friendship friendship = friendshipRepository.findFriendshipById(id);
        if (friendship == null) {
            throw new ApiException("Friendship not found");
        }
        friendship.setSenderId(dto.getSenderId());
        friendship.setReceiverId(dto.getReceiverId());
        friendship.setStatus(dto.getStatus());
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
