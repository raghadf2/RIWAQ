package com.example.riwaq.Repository;

import com.example.riwaq.Model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    Friendship findFriendshipById(Integer id);

    Friendship findFriendshipBySenderIdAndReceiverId(Integer senderId, Integer receiverId);

    List<Friendship> findFriendshipsByReceiverIdAndStatus(Integer receiverId, String status);

    List<Friendship> findFriendshipsBySenderIdAndStatus(Integer senderId, String status);

    @Query("select f from Friendship f where (f.receiverId = :userId or f.senderId = :userId) and f.status = :status")
    List<Friendship> findFriendshipsByUserIdAndStatus(Integer userId, String status);
}