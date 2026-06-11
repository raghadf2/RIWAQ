# RIWAQ

RIWAQ is a social reading platform that allows users to discover books, track reading progress, share reading experiences through posts and reviews, interact with friends, join reading spaces, and participate in reading challenges. The platform enhances the reading experience using AI-powered recommendations, discussion tools, smart notifications, WhatsApp alerts, and personalized analytics.

---

## 👩‍💻 Reading Space System (My Contribution)

My contribution focuses on building the **Reading Space System**, including reading spaces, space memberships, reading challenges, AI-powered discussion tools, smart notifications, WhatsApp winner alerts, and analytical endpoints.

---

## 📖 Space Model

| Feature                            | Endpoint                                         | Description                                                                                                                      |
| ---------------------------------- | ------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------- |
| Get Spaces by Reading Status       | `/api/v1/space/get/status/{userId}/{status}`     | Retrieves spaces based on the user's reading status for the related book.                                                        |
| Generate Discussion Questions (AI) | `/api/v1/space/suggestions/{bookId}`             | Uses AI to generate discussion questions for a selected book.                                                                    |
| Generate Reflection Questions (AI) | `/api/v1/space/reflection/{bookId}/{pageNumber}` | Uses AI to generate reflection prompts based on a book and page number.                                                          |
| Space Statistics                   | `/api/v1/space/statistics/{spaceId}`             | Provides analytical statistics for a reading space, including members count, reading statuses, average progress, and top reader. |

---

## 👥 Space Membership Model

| Feature                            | Endpoint                                                       | Description                                                                                                                                       |
| ---------------------------------- | -------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- |
| Get Friends Inside a Space         | `/api/v1/space-membership/friends/{spaceId}/{userId}`          | Retrieves the user's accepted friends who are already members of the same reading space.                                                          |
| Get Eligible Friends To Join Space | `/api/v1/space-membership/eligible-friends/{spaceId}/{userId}` | Retrieves accepted friends who are eligible to join a space based on friendship status, book ownership, reading progress, and current membership. |

---

## 🏆 Reading Challenge Model

| Feature                      | Endpoint                                                                                                                 | Description                                                                                                                                     |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| Respond to Challenge         | `/api/v1/reading-challenge/accept/{challengeId}/{userId}` <br> `/api/v1/reading-challenge/reject/{challengeId}/{userId}` | Allows the challenge receiver to accept or reject a pending reading challenge after validating the challenge status and receiver identity.      |
| Update Challenge Progress    | `/api/v1/reading-challenge/update-progress/{challengeId}/{userId}/{page}`                                                | Updates a participant's progress inside the challenge, prevents backward progress, detects the winner, and sends winner notifications.          |
| Get Challenges by Status     | `/api/v1/reading-challenge/get/status/{status}`                                                                          | Retrieves reading challenges based on their status.                                                                                             |
| Get Challenges by Date       | `/api/v1/reading-challenge/get/date`                                                                                     | Retrieves reading challenges within a selected date range.                                                                                      |
| Reading Challenge Scoreboard | `/api/v1/reading-challenge/scoreboard/{challengeId}`                                                                     | Displays a challenge scoreboard showing both participants' progress, remaining pages, current leader, challenge status, and winner information. |

---

## ✨ Additional Features

| Feature                   | Description                                                                                                                                          |
| ------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| 🔔 Notifications          | Sends notifications for challenge acceptance, challenge progress updates, winner announcements, book progress reminders, and reading-related events. |
| 📧 Email Integration      | Sends email notifications for important reading and challenge events.                                                                                |
| 📱 WhatsApp Integration   | Sends WhatsApp messages to both challenge participants when a winner is announced.                                                                   |
| 🤖 OpenAI Integration     | Generates AI-powered discussion questions, reflection questions, post analysis, and book-related insights.                                           |
| 📊 Personalized Analytics | Provides analytical endpoints such as space statistics and reading challenge scoreboard to improve user engagement.                                  |

