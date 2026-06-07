# RIWAQ

RIWAQ is a social reading platform that allows users to discover books, track reading progress, share reading experiences through posts and reviews, interact with friends, join reading spaces, and participate in reading challenges. The platform enhances the reading experience using AI-powered recommendations, discussion tools, smart notifications, reading streaks, and personalized analytics.

---

## 👩‍💻 Reading Space System (My Contribution)

### 📖 Space Module

| Feature | Endpoint |
|----------|----------|
| Get Spaces by Reading Status | `/api/v1/space/get/status/{userId}/{status}` |
| Generate Discussion Questions (AI) | `/api/v1/space/suggestions/{bookId}` |
| Generate Reflection Questions (AI) | `/api/v1/space/reflection/{bookId}/{pageNumber}` |

### 👥 Space Membership Module

| Feature | Endpoint |
|----------|----------|
| Get Friends Inside a Space | `/api/v1/space-membership/friends/{spaceId}/{userId}` |

### 🏆 Reading Challenge Module

| Feature | Endpoint |
|----------|----------|
| Accept Challenge | `/api/v1/reading-challenge/accept/{challengeId}/{userId}` |
| Reject Challenge | `/api/v1/reading-challenge/reject/{challengeId}/{userId}` |
| Update Challenge Progress | `/api/v1/reading-challenge/update-progress/{challengeId}/{userId}/{page}` |
| Get Challenges by Status | `/api/v1/reading-challenge/get/status/{status}` |
| Get Challenges by Date | `/api/v1/reading-challenge/get/date` |

### 🔥 Reading Streak

I implemented the Reading Streak logic and integrated it with the existing Update Reading Progress endpoint.
| Feature | Endpoint |
|----------|----------|
| Reading Streak Logic | `/api/v1/user-book/update-progress/{userBookId}` |

### ✨ Additional Features

| Feature | Description |
|----------|-------------|
| 🔔 Notifications | Challenge accepted, challenge progress and winner announcement |
| 📧 Email Integration | Sends email notifications for important reading and challenge events |
| 📱 WhatsApp Integration | Sends challenge winner notifications |
| 🤖 OpenAI Integration | Generates discussion questions and reflection questions for books |
