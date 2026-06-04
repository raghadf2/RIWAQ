package com.example.riwaq.Repository;

import com.example.riwaq.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Notification findNotificationById(Integer id);
//    List<Notification> findNotificationsByRecipientId(Integer recipientId);
//    List<Notification> findNotificationsByRecipientIdAndStatus(Integer recipientId, String status);
    List<Notification> findNotificationsByUserId(Integer userId);

}
