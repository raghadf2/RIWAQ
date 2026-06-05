package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;

import com.example.riwaq.DTO.OUT.NotificationDTOOut;
import com.example.riwaq.Model.Notification;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.NotificationRepository;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<NotificationDTOOut> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTOOut> getNotificationsByUserId(Integer userId) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        List<Notification> notifications =
                notificationRepository.findNotificationsByUserId(userId);

        if (notifications.isEmpty()) {
            throw new ApiException("No notifications found");
        }

        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void sendWelcomeNotification(Integer userId) {

        String message =
                "مرحبًا بك في رواق 📚، ابدأ رحلتك القرائية اليوم.";

        sendNotification(userId,
                "WELCOME",
                message);
    }

    public void sendBookAddedNotification(Integer userId,
                                          String bookTitle) {

        String message =
                "تمت إضافة كتاب " + bookTitle + " إلى قائمة قراءاتك.";

        sendNotification(userId,
                "BOOK_ADDED",
                message);
    }

    public void sendBookCompletedNotification(Integer userId,
                                              String bookTitle) {

        String message =
                "أحسنت! لقد أنهيت قراءة كتاب " + bookTitle + ". استمر في رحلتك القرائية.";

        sendNotification(userId,
                "BOOK_COMPLETED",
                message);
    }
    public void sendSimilarBooksNotification(Integer userId,
                                             String bookTitle,
                                             List<String> similarBooks) {
        String booksText = "";

        for (String book : similarBooks) {
            booksText = booksText + "• " + book.trim() + "\n";
        }

        String message =
                "بناءً على آخر كتاب قرأته: "
                        + bookTitle
                        + "\n\n🧠📚 مساعد رواق الذكي اختار لك 5 كتب قد تعجبك:\n\n"
                        + booksText;

        sendNotification(
                userId,
                "SIMILAR_BOOKS",
                message
        );
    }

    public void sendPostAboutCurrentBookNotification(Integer userId,
                                                     String bookTitle,
                                                     Integer postId) {

        String message =
        "تمت مشاركة منشور جديد حول كتاب " + bookTitle +
        ". اكتشف آراء القرّاء وتفاعل معهم.";

        sendNotification(userId,
                "POST_ABOUT_CURRENT_BOOK",
                message,
                postId,
                "POST");
    }

    public void sendPostAboutCurrentBookNotification(Integer userId,
                                                     String bookTitle,
                                                     Integer postId,
                                                     String postType,
                                                     String summary) {

        String message =
                "تمت مشاركة منشور جديد حول كتاب \"" + bookTitle + "\".";

        if (postType != null && !postType.isBlank()) {
            message += "\n\nنوع المنشور: " + (postType);
        }

        if (summary != null && !summary.isBlank()) {
            message = message + "\n" + "ملخص المنشور: " + summary;
        }

        sendNotification(userId,
                "POST_ABOUT_CURRENT_BOOK",
                message,
                postId,
                "POST");
    }

    public void sendAnalyzedPostAboutCurrentBookNotification(Integer userId,
                                                             String bookTitle,
                                                             Integer postId,
                                                             String postType,
                                                             String summary) {

        String message =
                          "\n\n📖 " + bookTitle
                        + "\n\n" + getPostTypeMessage(postType)
                        + "\n\n\"" + summary + "\"";

        sendNotification(userId,
                "POST_ABOUT_CURRENT_BOOK",
                message,
                postId,
                "POST");
    }

    private String getPostTypeMessage(String postType) {
        if (postType == null) {
            return "تمت مشاركة منشور جديد:";
        }

        return switch (postType) {
            case "Recommendation" -> "تمت مشاركة توصية جديدة:";
            case "Criticism" -> "تمت مشاركة انتقاد جديد:";
            case "Discussion" -> "تمت مشاركة نقاش جديد:";
            case "Question" -> "تمت مشاركة سؤال جديد:";
            case "Reflection" -> "تمت مشاركة تأمل جديد:";
            default -> "تمت مشاركة منشور جديد:";
        };
    }

    private void sendNotification(Integer userId,
                                  String type,
                                  String message) {
        sendNotification(userId, type, message, null, null);
    }

    private void sendNotification(Integer userId,
                                  String type,
                                  String message,
                                  Integer referenceId,
                                  String referenceType) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        String subject;

        switch (type) {

            case "WELCOME":
                subject = "مرحبًا بك في رواق 📚";
                break;

            case "BOOK_ADDED":
                subject = "تمت إضافة كتاب إلى قائمة قراءاتك 📖";
                break;

            case "BOOK_COMPLETED":
                subject = "تهانينا على إنهاء الكتاب 🎉";
                break;

            case "POST_ABOUT_CURRENT_BOOK":
            subject = "منشور جديد حول كتابك الحالي 📝";
            break;

            default:
                subject = "إشعار من رواق";
        }

        if ("POST_ABOUT_CURRENT_BOOK".equals(type)) {
            subject = "💬 منشور جديد حول كتابك الحالي";
        }

        try {
//            emailService.sendEmail(
//                    user.getEmail(),
//                    subject,
//                    message
//            String htmlMessage =
//                    buildEmailTemplate(subject, message);
            String htmlMessage =
                    buildEmailTemplate(subject, message.replace("\n", "<br>"));

            emailService.sendEmail(
                    user.getEmail(),
                    subject,
                    htmlMessage
            );
        } catch (Exception e) {
            System.out.println("Email not sent");
        }

        Notification notification = new Notification();

        notification.setUserId(user.getId());
        notification.setType(type);
        notification.setMessage(message);
        notification.setReferenceId(referenceId);
        notification.setReferenceType(referenceType);

        notificationRepository.save(notification);
    }
// Added an email template to improve how notification emails look for users.
    private String buildEmailTemplate(String title, String message) {

        return """
            <div dir="rtl" style="font-family: Arial; background-color:#f7f5ef; padding:24px;">
                <div style="max-width:600px; margin:auto; background-color:white; border-radius:12px; padding:24px;">
                    <h2 style="color:#5a3e2b;">رواق 📚</h2>
                    <h3>%s</h3>
                    <p style="font-size:16px; line-height:1.8;">%s</p>
                    <hr>
                    <p style="font-size:13px; color:#888;">هذه رسالة تلقائية من منصة رواق.</p>
                </div>
            </div>
            """.formatted(title, message);
    }

    private NotificationDTOOut convertToDTO(Notification notification) {
        return new NotificationDTOOut(
                notification.getId(),
                notification.getUserId(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getType(),
                notification.getReferenceId(),
                notification.getReferenceType(),
                notification.getCreatedAt(),
                notification.getSentByEmail(),
                notification.getSentByWhatsApp()
        );
    }
}
