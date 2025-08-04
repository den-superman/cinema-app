# 🎬 Cinema Booking App

A simple web application for managing movies, movie sessions, and ticket bookings.

## 🚀 Tech Stack

- Java 17
- Spring Boot
- Spring MVC + Security
- Thymeleaf
- PostgreSQL
- HTML/CSS/JS

## 🔧 Functionality

- ✅ Register/login/logout (with role-based access)
- ✅ Browse available movies and sessions
- ✅ View movie descriptions and comments
- ✅ View available seats and book tickets
- ✅ Add comments to movies (AI moderated)
- ✅ Admin panel for:
    - Adding movies
    - Adding movie sessions
    - Deleting comments

## 🛠️ Getting Started

### 1. Clone the repo

```bash
git clone https://github.com/your-username/cinema-booking-app.git
cd cinema-booking-app
```

### 2. Set environment variables
You need to define the following environment variables before running the app:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/your_db
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export OPEN_ROUTER_KEY=your_openrouter_api_key
```
### 3. Run the application
```bash
mvn spring-boot:run
```
Or if you're using IntelliJ, just run the main class with the environment variables set.

### 4. Access it at: http://localhost:8080