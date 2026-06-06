package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.Out.SpaceMembershipDTOOut;
import com.example.riwaq.DTO.Out.UserDtoOut;
import com.example.riwaq.Model.*;
import com.example.riwaq.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceMembershipService {

    private final SpaceMembershipRepository spaceMembershipRepository;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final FriendshipRepository friendshipRepository;

    public void addMember(Integer spaceId, Integer userId) {
        Space space = spaceRepository.findSpaceBySpaceId(spaceId);

        if (space == null) {
            throw new ApiException("Space not found");
        }

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        SpaceMembership existing = spaceMembershipRepository.findSpaceMembershipBySpaceIdAndUserId(spaceId, userId);

        if (existing != null) {
            throw new ApiException("User already joined this space");
        }

        UserBook userBook = userBookRepository.findUserBookByUserIdAndBookId(userId, space.getBookId());

        if (userBook == null) {
            throw new ApiException("User must add this book before joining the space");
        }

        if (userBook.getStatus().equalsIgnoreCase("NOT_STARTED")) {
            throw new ApiException("User must start reading or complete the book before joining the space");
        }

        SpaceMembership membership = new SpaceMembership();
        membership.setSpaceId(spaceId);
        membership.setUserId(userId);
        membership.setJoinedAt(LocalDateTime.now());

        spaceMembershipRepository.save(membership);
    }

    public List<SpaceMembershipDTOOut> getAllMemberships() {

        List<SpaceMembership> memberships = spaceMembershipRepository.findAll();
        List<SpaceMembershipDTOOut> dtoOutList = new ArrayList<>();

        for (SpaceMembership membership : memberships) {
            dtoOutList.add(convertToDTOOut(membership));
        }

        return dtoOutList;
    }

    public List<SpaceMembershipDTOOut> getMembersBySpace(Integer spaceId) {
        Space space = spaceRepository.findSpaceBySpaceId(spaceId);

        if (space == null) {
            throw new ApiException("Space not found");
        }

        List<SpaceMembership> memberships = spaceMembershipRepository.findAllBySpaceId(spaceId);

        List<SpaceMembershipDTOOut> dtoOutList = new ArrayList<>();

        for (SpaceMembership membership : memberships) {
            dtoOutList.add(convertToDTOOut(membership));
        }

        return dtoOutList;
    }

    public void removeMember(Integer spaceId, Integer memberId, Integer requesterId) {

        Space space = spaceRepository.findSpaceBySpaceId(spaceId);
        if (space == null) {
            throw new ApiException("Space not found");
        }

        User requester = userRepository.findUserById(requesterId);
        if (requester == null) {
            throw new ApiException("Requester not found");
        }

        User member = userRepository.findUserById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        SpaceMembership membership =
                spaceMembershipRepository.findSpaceMembershipBySpaceIdAndUserId(spaceId, memberId);

        if (membership == null) {
            throw new ApiException("Membership not found");
        }

        // العضو يطلع بنفسه أو صاحب السبيس يحذفه
        if (!memberId.equals(requesterId) && !space.getCreatorId().equals(requesterId)) {
            throw new ApiException("Only the member or space creator can remove this membership");
        }

        spaceMembershipRepository.delete(membership);
    }

    private SpaceMembershipDTOOut convertToDTOOut(SpaceMembership membership) {

        SpaceMembershipDTOOut dtoOut = new SpaceMembershipDTOOut();

        dtoOut.setMembershipId(membership.getMembershipId());
        dtoOut.setSpaceId(membership.getSpaceId());
        dtoOut.setUserId(membership.getUserId());
        dtoOut.setJoinedAt(membership.getJoinedAt());

        return dtoOut;
    }

    //==================


    public List<UserDtoOut> getFriendsInSpace(Integer spaceId, Integer userId) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        Space space = spaceRepository.findSpaceBySpaceId(spaceId);

        if (space == null) {
            throw new ApiException("Space not found");
        }

        List<SpaceMembership> memberships = spaceMembershipRepository.findAllBySpaceId(spaceId);

        List<UserDtoOut> friendsInSpace = new ArrayList<>();

        for (SpaceMembership membership : memberships) {

            Integer memberId = membership.getUserId();

            if (memberId.equals(userId)) {
                continue;
            }

            Friendship friendship = friendshipRepository.findFriendshipBySenderIdAndReceiverId(userId, memberId);

            if (friendship == null) {

                friendship = friendshipRepository.findFriendshipBySenderIdAndReceiverId(memberId, userId);
            }

            if (friendship != null && friendship.getStatus().equalsIgnoreCase("ACCEPTED")) {

                User friend = userRepository.findUserById(memberId);

                UserDtoOut dto = new UserDtoOut();

                dto.setName(friend.getName());
                dto.setUsername(friend.getUsername());

                friendsInSpace.add(dto);
            }
        }

        return friendsInSpace;
    }

}
