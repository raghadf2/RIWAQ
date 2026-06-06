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

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }
        String message =
                user.getName()
                        + "، يسعدنا انضمامك إلينا! نتمنى لك رحلة قرائية ممتعة✨";


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
                        + "\n\n مساعد رواق الذكي اختار لك 5 كتب قد تعجبك:\n\n"
                        + booksText;

        sendNotification(
                userId,
                "SIMILAR_BOOKS",
                message
        );
    }
    public void sendProgressReminderNotification(Integer userId,
                                                 String bookTitle,
                                                 Integer progressPercentage) {

        String message =
                "📖 لقد قطعت شوطًا رائعًا في كتاب "
                        + bookTitle
                        + " ووصلت إلى "
                        + progressPercentage
                        + "%.\n\n"
                        + "أوشكت على إنهائه، أخبرنا إذا أحرزت تقدمًا جديدًا. نحن نشجعك!";

        sendNotification(
                userId,
                "PROGRESS_REMINDER",
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
            case "SIMILAR_BOOKS":
                subject = "اقتراحات كتب مشابهة لك 🧠📚";
                break;
            case "PROGRESS_REMINDER":
                subject = "أوشكت على إنهاء كتابك 📖";
                break;

            case "POST_ABOUT_CURRENT_BOOK":
            subject = "منشور جديد حول كتابك الحالي 📝";
            break;

            case "CHALLENGE_PROGRESS":
                subject = "تحديث جديد في تحدي القراءة 📖";
                break;

            case "CHALLENGE_WINNER":
                subject = "تم إعلان فائز تحدي القراءة 🏆";
                break;

            default:
                subject = "إشعار من رواق 📚";
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
            System.out.println("Email not sent: " + e.getMessage());
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
            <div dir="rtl" style="font-family: Arial, sans-serif; background-color:#f3eee6; padding:32px;">
                <div style="max-width:620px; margin:auto; background-color:#ffffff; border-radius:18px; overflow:hidden; border:1px solid #d8bfa5;">
                    
                    <div style="background-color:#ffffff; padding:28px 28px 12px; text-align:right;">
                        <h1 style="margin:0; color:#9b632d; font-size:42px; font-weight:bold;">رواق</h1>
                        <p style="margin:6px 0 0; color:#9b632d; font-size:16px;">
                            رفيقك الذكي في رحلتك القرائية
                        </p>
                    </div>

                    <div style="height:8px; background-color:#9b632d;"></div>

                    <div style="padding:30px 28px; text-align:right;">
                        <h2 style="color:#4b2f1b; font-size:22px; margin:0 0 18px;">
                            %s
                        </h2>

                        <p style="font-size:17px; line-height:2; color:#222; margin:0;">
                            %s
                        </p>
                    </div>

                    <div style="background-color:#dcc5ad; padding:18px 28px; text-align:center;">
                        <p style="font-size:13px; color:#6b4426; margin:0;">
                            - هذه رسالة تلقائية من منصة رواق -
                        </p>
                    </div>
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

    //===============
    public void sendChallengeProgressNotification(Integer userId, Integer challengeId, Integer progressUserId, String bookTitle, Integer page, Integer totalPages) {

        String message =
                "📖 تم تحديث تقدم القراءة في تحدي كتاب "
                        + bookTitle
                        + ".\n"
                        + "المستخدم رقم " + progressUserId
                        + " وصل إلى الصفحة "
                        + page
                        + " من "
                        + totalPages
                        + ".";

        sendNotification(
                userId,
                "CHALLENGE_PROGRESS",
                message,
                challengeId,
                "READING_CHALLENGE"
        );
    }

    public void sendChallengeWinnerNotification(Integer userId, Integer challengeId, Integer winnerId, String bookTitle) {

        String message =
                "🏆 انتهى تحدي قراءة كتاب "
                        + bookTitle
                        + ".\n"
                        + "الفائز هو المستخدم رقم "
                        + winnerId
                        + "!";

        sendNotification(
                userId,
                "CHALLENGE_WINNER",
                message,
                challengeId,
                "READING_CHALLENGE"
        );
    }

    public void sendChallengeAcceptedNotification(Integer userId, Integer challengeId, Integer receiverId, String bookTitle) {
        String message =
                "✅ تم قبول تحدي قراءة كتاب "
                        + bookTitle
                        + ".\n"
                        + "المستخدم رقم "
                        + receiverId
                        + " قبل التحدي وبدأت المنافسة!";

        sendNotification(
                userId,
                "CHALLENGE_ACCEPTED",
                message,
                challengeId,
                "READING_CHALLENGE"
        );
    }
}
