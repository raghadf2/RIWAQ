package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.SpaceMembershipDTOIn;
import com.example.riwaq.DTO.OUT.SpaceMembershipDTOOut;
import com.example.riwaq.Model.Space;
import com.example.riwaq.Model.SpaceMembership;
import com.example.riwaq.Repository.SpaceMembershipRepository;
import com.example.riwaq.Repository.SpaceRepository;
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

    public void addMembership(SpaceMembershipDTOIn dto) {

        Space space = spaceRepository.findSpaceBySpaceId(dto.getSpaceId());

        if (space == null) {
            throw new ApiException("Space not found");
        }

        boolean exists = spaceMembershipRepository.existsByUserIdAndSpaceId(dto.getUserId(), dto.getSpaceId());

        if (exists) {
            throw new ApiException("User already joined this space");
        }

        SpaceMembership membership = new SpaceMembership();

        membership.setUserId(dto.getUserId());
        membership.setSpaceId(dto.getSpaceId());
        membership.setJoinedAt(LocalDateTime.now());

        spaceMembershipRepository.save(membership);
    }

    public List<SpaceMembershipDTOOut> getAllMemberships(){

        List<SpaceMembership> memberships = spaceMembershipRepository.findAll();

        List<SpaceMembershipDTOOut> dtoOutList = new ArrayList<>();

        for(SpaceMembership membership : memberships){

            SpaceMembershipDTOOut dtoOut = new SpaceMembershipDTOOut();

            dtoOut.setMembershipId(membership.getMembershipId());
            dtoOut.setSpaceId(membership.getSpaceId());
            dtoOut.setUserId(membership.getUserId());
            dtoOut.setJoinedAt(membership.getJoinedAt());

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }

    public void updateMembership(Integer membershipId, SpaceMembershipDTOIn dto) {

        SpaceMembership membership = spaceMembershipRepository.findSpaceMembershipByMembershipId(membershipId);

        if (membership == null) {
            throw new ApiException("Membership not found");
        }

        if (dto.getSpaceId() != null) {

            Space space = spaceRepository.findSpaceBySpaceId(dto.getSpaceId());

            if (space == null) {
                throw new ApiException("Space not found");
            }

            boolean exists = spaceMembershipRepository.existsByUserIdAndSpaceId(dto.getUserId(), dto.getSpaceId());

            if (exists) {
                throw new ApiException("User already joined this space");
            }

            membership.setSpaceId(dto.getSpaceId());
        }

        if (dto.getUserId() != null) {
            membership.setUserId(dto.getUserId());
        }

        spaceMembershipRepository.save(membership);
    }

    public void deleteMembership(Integer membershipId) {

        SpaceMembership membership = spaceMembershipRepository.findSpaceMembershipByMembershipId(membershipId);

        if (membership == null) {
            throw new ApiException("Membership not found");
        }

        spaceMembershipRepository.delete(membership);
    }
}
