package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;

import com.example.riwaq.Model.Notification;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.NotificationRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByUserId(Integer userId) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        List<Notification> notifications =
                notificationRepository.findNotificationsByUserId(userId);

        if (notifications.isEmpty()) {
            throw new ApiException("No notifications found");
        }

        return notifications;
    }

    public void sendWelcomeNotification(Integer userId) {

        String message =
//                "Welcome to Riwaq 📚 Start your reading journey today."
                "مرحبًا بك في رواق 📚، ابدأ رحلتك القرائية اليوم."  ;

        sendNotification(userId,
                "WELCOME",
                message);
    }

    public void sendBookAddedNotification(Integer userId,
                                          String bookTitle) {

        String message =
//                bookTitle + " has been added to your reading list."
                "تمت إضافة كتاب " + bookTitle + " إلى قائمة قراءاتك." ;

        sendNotification(userId,
                "BOOK_ADDED",
                message);
    }

    public void sendBookCompletedNotification(Integer userId,
                                              String bookTitle) {

        String message =
//                "Congratulations! You completed "
//                        + bookTitle
//                        + ". Keep reading and discover your next favorite book."
        "أحسنت! لقد أنهيت قراءة كتاب " + bookTitle + ". استمر في رحلتك القرائية.";

        sendNotification(userId,
                "BOOK_COMPLETED",
                message);
    }

    private void sendNotification(Integer userId,
                                  String type,
                                  String message) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Riwaq Notification",
                    message
            );
        } catch (Exception e) {
            System.out.println("Email not sent");
        }
        Notification notification = new Notification();

        notification.setUserId(user.getId());
        notification.setType(type);
        notification.setMessage(message);

        notificationRepository.save(notification);
    }
}