package com.example.riwaq.Repository;

import com.example.riwaq.Model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceRepository  extends JpaRepository<Space,Integer> {
    Optional<Space> findBySpaceId(Integer spaceId);
}

