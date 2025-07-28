# Используем официальный образ Java
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Кэш зависимостей
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем весь проект и собираем
COPY . .
RUN mvn clean package -DskipTests

# Runtime-образ для запуска
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Порт для Render (можно указать 10000, но лучше использовать 8080)
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
