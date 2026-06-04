package com.example.riwaq.Repository;

import com.example.riwaq.Model.SpaceMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceMembershipRepository  extends JpaRepository<SpaceMembership,Integer> {

    List<SpaceMembership> findBySpaceId(Integer spaceId);
    List<SpaceMembership> findByUserId(Integer userId);
}
