package com.example.riwaq.Repository;

import com.example.riwaq.Model.SpaceMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceMembershipRepository  extends JpaRepository<SpaceMembership,Integer> {
    SpaceMembership findSpaceMembershipByMembershipId(Integer membershipId);
    SpaceMembership findSpaceMembershipBySpaceIdAndUserId(Integer spaceId, Integer userId);
    List<SpaceMembership> findAllBySpaceId(Integer spaceId);

}
