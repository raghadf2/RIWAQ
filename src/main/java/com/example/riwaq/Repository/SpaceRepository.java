package com.example.riwaq.Repository;

import com.example.riwaq.Model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpaceRepository  extends JpaRepository<Space,Integer> {
    Space findSpaceBySpaceId(Integer spaceId);
    Space findSpaceByBookIdAndName(Integer bookId, String name);
    Boolean existsByBookIdAndName(Integer bookId, String name);
}

