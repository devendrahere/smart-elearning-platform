# 📚 EduSmart - Smart E-Learning Platform


A modern, feature-rich Learning Management System (LMS) built with Spring Boot 3, featuring real-time notifications, interactive assessments, JWT security, Redis caching, and a responsive user interface. EduSmart provides a complete educational platform similar to Coursera, Udemy, and Khan Academy.


## 🌟 Key Features

### 🔐 Advanced Security & Authentication
- **JWT-based Authentication** with access and refresh tokens
- **Role-Based Access Control** (Admin, Instructor, Student)
- **BCrypt Password Encryption** for secure credential storage
- Custom authentication filters and user details service
- Fine-grained API access control with Spring Security 6

### 📖 Comprehensive Course Management
- Create and organize courses with categories
- Multi-level content structure (Courses → Lessons )
- Instructor dashboard for course creation and management
- Student enrollment and course access system

### ✅ Interactive Assessment System
- Quiz builder with multiple question types
- Automated grading system
- Detailed result analysis and feedback

### 🔔 Real-Time Features
- **WebSocket + STOMP** for instant messaging
- **Server-Sent Events (SSE)** for live notifications
- Real-time course announcements
- Live discussion updates
- User presence indicators (online/offline status)

### 💬 Discussion Forum System
- Thread-based discussions for each course
- Real-time commenting and replies
- Nested conversation threads
- Community engagement features

### 📊 Analytics & Reporting
- Comprehensive learning analytics dashboard
- Course performance metrics
- Platform usage statistics
- Activity logs and audit trails

### ⚡ Performance Optimization
- **Redis Integration** for caching frequently accessed data
- Connection pooling configuration
- Spring Boot Actuator for monitoring
- Health checks and metrics endpoints

### 🎨 Modern Responsive UI
- Thymeleaf template engine with Bootstrap 5
- Clean and intuitive user interface
- Dynamic content loading
- Interactive JavaScript components
- Custom CSS styling

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2+
- **Security**: Spring Security 6 with JWT
- **Data Access**: Spring Data JPA
- **Caching**: Redis with Spring Cache
- **Real-Time**: WebSocket (STOMP), SSE
- **Monitoring**: Spring Boot Actuator
- **Build Tool**: Maven

### Frontend
- **Template Engine**: Thymeleaf
- **CSS Framework**: Bootstrap 5
- **JavaScript**: ES6+ with WebSocket client
- **Icons**: Font Awesome / Bootstrap Icons

### Database
- **Primary Database**: MySQL / PostgreSQL
- **Caching Layer**: Redis
- **Schema Management**: Hibernate with JPA

### DevOps
- **Version Control**: Git

## 📁 Project Structure

```
smart-elearning-platform/
│
├── src/main/java/com/edusmart/
│   ├── EduSmartApplication.java          # Main application entry point
│   │
│   ├── config/                            # Configuration classes
│   │   ├── RedisConfig.java              # Redis configuration
│   │   ├── SecurityConfig.java           # Security & JWT config
│   │   ├── WebConfig.java                # Web MVC configuration
│   │   └── WebSocketConfig.java          # WebSocket configuration
│   │
│   ├── security/                          # Security components
│   │   ├── CustomUserDetailsService.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtTokenProvider.java
│   │
│   ├── controller/                        # REST API Controllers
│   │   ├── AuthController.java
│   │   ├── CourseController.java
│   │   ├── CategoryController.java
│   │   ├── LessonController.java
│   │   ├── QuizController.java
│   │   ├── EnrollmentController.java
│   │   ├── NotificationController.java
│   │   ├── DiscussionController.java
│   │   ├── AnnouncementController.java
│   │   ├── CertificateController.java
│   │   ├── AnalyticsController.java
│   │   ├── UserController.java
│   │   └── FileResourceController.java
│   │
│   ├── controller/websocket/              # WebSocket Controllers
│   │   ├── AnnouncementWebSocketController.java
│   │   ├── NotificationWebSocketController.java
│   │   └── DiscussionWebSocketController.java
│   │
│   ├── controller/ui/                     # Thymeleaf UI Controllers
│   │   ├── HomeUIController.java
│   │   ├── CourseUIController.java
│   │   ├── DiscussionUIController.java
│   │   └── AnalyticsUIController.java
│   │
│   ├── dto/                               # Data Transfer Objects
│   │   ├── CourseDTO.java
│   │   ├── LessonDTO.java
│   │   ├── QuizDTO.java
│   │   ├── NotificationDTO.java
│   │   └── ...
│   │
│   ├── entity/                            # JPA Entities
│   │   ├── User.java
│   │   ├── Role.java
│   │   ├── Course.java
│   │   ├── Lesson.java
│   │   ├── Quiz.java
│   │   ├── QuizAttempt.java
│   │   ├── Discussion.java
│   │   ├── Announcement.java
│   │   ├── Notification.java
│   │   ├── Certificate.java
│   │   └── ...
│   │
│   ├── repository/                        # JPA Repositories
│   │   ├── UserRepository.java
│   │   ├── CourseRepository.java
│   │   ├── LessonRepository.java
│   │   └── ...
│   │
│   ├── service/                           # Service Layer
│   │   ├── implemented/                   # Service implementations
│   │   └── ui/                            # UI-specific services
│   │
│   └── exception/                         # Custom exceptions
│
├── src/main/resources/
│   ├── application.yml                    # Main configuration
│   ├── application-dev.yml                # Development config
│   ├── application-prod.yml               # Production config
│   │
│   ├── static/                            # Static resources
│   │   ├── css/                           # Custom stylesheets
│   │   ├── js/                            # JavaScript files
│   │   │   └── websocket-setup.js
│   │   └── images/                        # Image assets
│   │
│   └── templates/                         # Thymeleaf templates
│       ├── home.html
│       ├── login.html
│       ├── register.html
│       ├── courses.html
│       ├── course-details.html
│       ├── lesson-view.html
│       ├── quiz-*.html
│       ├── discussion-*.html
│       ├── announcements.html
│       └── analytics.html
│
├── src/test/java/                         # Test files
├── docker-compose.yml                     # Docker services
├── pom.xml                                # Maven dependencies
└── README.md                              # This file
```

## 🚀 Getting Started

### Prerequisites

- **Java 17+** installed
- **Maven 3.6+** installed
- **MySQL 8.0+** or **PostgreSQL 12+**
- **Redis 6.0+** (optional but recommended)
- **Docker** (optional, for containerization)

### Installation Steps

#### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/smart-elearning-platform.git
cd smart-elearning-platform
```

#### 2️⃣ Configure Database

Create a MySQL database:

```sql
CREATE DATABASE edusmart;
```

#### 3️⃣ Configure Application Properties

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/edusmart
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  
  redis:
    host: localhost
    port: 6379
    timeout: 60000

jwt:
  secret: your-secret-key-here-minimum-256-bits
  expiration: 86400000  # 24 hours
```

#### 4️⃣ Start Redis (Optional)

**Using Docker:**
```bash
docker run -d -p 6379:6379 redis:latest
```

**Or using Docker Compose:**
```bash
docker-compose up -d redis
```

#### 5️⃣ Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

#### 6️⃣ Access the Application

Open your browser and navigate to:
- **Application**: http://localhost:8080
- **Actuator Health**: http://localhost:8080/actuator/health

### 🐳 Docker Deployment

Run the entire stack with Docker Compose:

```bash
docker-compose up -d
```

This will start:
- MySQL database
- Redis cache
- EduSmart application

## 📡 API Endpoints

### Authentication
```
POST   /api/auth/register          # Register new user
POST   /api/auth/login             # Login and get JWT token
POST   /api/auth/refresh           # Refresh access token
```

### Courses
```
GET    /api/courses                # Get all courses
POST   /api/courses                # Create new course (Instructor/Admin)
GET    /api/courses/{id}           # Get course details
PUT    /api/courses/{id}           # Update course (Instructor/Admin)
DELETE /api/courses/{id}           # Delete course (Admin)
GET    /api/courses/category/{id}  # Get courses by category
```

### Lessons
```
GET    /api/lessons/{id}           # Get lesson details
POST   /api/lessons                # Create lesson (Instructor)
PUT    /api/lessons/{id}           # Update lesson (Instructor)
DELETE /api/lessons/{id}           # Delete lesson (Instructor)
```

### Quizzes
```
GET    /api/quizzes/{id}           # Get quiz details
POST   /api/quizzes                # Create quiz (Instructor)
POST   /api/quizzes/{id}/attempt   # Submit quiz attempt
GET    /api/quizzes/results/{id}   # Get quiz results
```

### Enrollment
```
POST   /api/enrollments            # Enroll in course
GET    /api/enrollments/my-courses # Get user's enrolled courses
DELETE /api/enrollments/{id}       # Unenroll from course
```

### Discussions
```
GET    /api/discussions            # Get all discussions
POST   /api/discussions            # Create discussion thread
POST   /api/discussions/{id}/reply # Reply to discussion
GET    /api/discussions/course/{id}# Get course discussions
```

### Notifications (WebSocket)
```
WS     /ws/notifications           # WebSocket endpoint
STOMP  /app/subscribe              # Subscribe to notifications
STOMP  /topic/announcements        # Announcement topic
STOMP  /queue/user-notifications   # User-specific queue
```

### Analytics
```
GET    /api/analytics/dashboard    # Get platform statistics
GET    /api/analytics/course/{id}  # Get course analytics
GET    /api/analytics/user/{id}    # Get user progress
```

## 🧪 Testing

Run unit tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

Run specific test class:
```bash
mvn test -Dtest=CourseServiceTest
```

## 🔒 Security Features

### JWT Token Structure
- **Access Token**: Short-lived (24 hours)
- **Refresh Token**: Long-lived (7 days)
- **Token Payload**: User ID, roles, expiration

### Role Hierarchy
```
ADMIN > INSTRUCTOR > STUDENT
```

### Protected Endpoints
- `/api/admin/**` - Admin only
- `/api/instructor/**` - Instructor and Admin
- `/api/student/**` - All authenticated users

## 📊 Monitoring & Health Checks

Spring Boot Actuator endpoints:

```
GET /actuator/health              # Application health
GET /actuator/metrics             # Application metrics
GET /actuator/info                # Application info
GET /actuator/prometheus          # Prometheus metrics
```

## 🎯 Performance Optimization

### Caching Strategy

```java
@Cacheable(value = "courses", key = "#categoryId")
public List<Course> getCoursesByCategory(Long categoryId) {
    return courseRepository.findByCategoryId(categoryId);
}

@CacheEvict(value = "courses", allEntries = true)
public Course createCourse(Course course) {
    return courseRepository.save(course);
}
```

## 🌐 Environment Configuration

### Development
```yaml
server:
  port: 8080
spring:
  profiles:
    active: dev
```

### Production
```yaml
server:
  port: 80
spring:
  profiles:
    active: prod
  jpa:
    hibernate:
      ddl-auto: validate
```

## 📝 Sample Users

After first run, you can create these sample users:

| Email | Password | Role |
|-------|----------|------|
| admin@edusmart.com | admin123 | ADMIN |
| instructor@edusmart.com | instructor123 | INSTRUCTOR |
| student@edusmart.com | student123 | STUDENT |


## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Author

- **Devendra C** - [devendrahere](https://github.com/devendrahere)
