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
 
## 📸 Screenshots

- Home page  
  <img width="1919" height="913" alt="image" src="https://github.com/user-attachments/assets/81e5df08-dc9a-4876-8fab-c31cb74440fc" />

- My tickets  
  <img width="1914" height="911" alt="image" src="https://github.com/user-attachments/assets/969fb2c1-63ef-4955-9c78-2a55f8eedfdf" />

- Booking form  
  <img width="1918" height="910" alt="image" src="https://github.com/user-attachments/assets/07a94ee7-d277-432b-aa1e-678c6fd1c8ff" />

- Admin view
  <img width="1919" height="906" alt="image" src="https://github.com/user-attachments/assets/41f31792-4601-43db-b405-a340758db3e6" />

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

