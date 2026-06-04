package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.SpaceMembershipDTOIn;
import com.example.riwaq.DTO.OUT.SpaceMembershipDTOOut;
import com.example.riwaq.Model.SpaceMembership;
import com.example.riwaq.Repository.SpaceMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceMembershipService {

    private final SpaceMembershipRepository spaceMembershipRepository;

    public void addMembership(PostDTOIn.SpaceMembershipDTOIn dto){

        SpaceMembership membership = new SpaceMembership();

        membership.setSpaceId(dto.getSpaceId());
        membership.setUserId(dto.getUserId());
        membership.setJoinedAt(LocalDateTime.now());

        spaceMembershipRepository.save(membership);
    }

    public List<FriendshipDTOOut.SpaceMembershipDTOOut> getAllMemberships(){

        List<SpaceMembership> memberships = spaceMembershipRepository.findAll();

        List<FriendshipDTOOut.SpaceMembershipDTOOut> dtoOutList = new ArrayList<>();

        for(SpaceMembership membership : memberships){

            FriendshipDTOOut.SpaceMembershipDTOOut dtoOut = new FriendshipDTOOut.SpaceMembershipDTOOut();

            dtoOut.setMembershipId(membership.getMembershipId());
            dtoOut.setSpaceId(membership.getSpaceId());
            dtoOut.setUserId(membership.getUserId());
            dtoOut.setJoinedAt(membership.getJoinedAt());

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }

    public void updateMembership(Integer membershipId,
                                 SpaceMembershipDTOIn dto){

//        SpaceMembership membership = spaceMembershipRepository
//                .findByMembershipId(membershipId)
//                .orElseThrow(() ->
//                        new ApiException("Membership not found"));
//
//        membership.setSpaceId(dto.getSpaceId());
//        membership.setUserId(dto.getUserId());
//
//        spaceMembershipRepository.save(membership);
//    }

//    public void deleteMembership(Integer membershipId){
//
    }

    public void deleteMembership(Integer membershipId){

//        SpaceMembership membership = spaceMembershipRepository
//                .findByMembershipId(membershipId)
//                .orElseThrow(() ->
//                        new ApiException("Membership not found"));
//
//        spaceMembershipRepository.delete(membership);
//    }
    }
}
