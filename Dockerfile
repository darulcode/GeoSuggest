# Tahap 1: Build Maven
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Salin semua file source ke dalam container
COPY . .

# Jalankan build dengan Maven
RUN mvn clean package -DskipTests

# Tahap 2: Jalankan aplikasi dengan OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Salin hasil build dari tahap pertama
COPY --from=builder /app/target/GeoCitySuggest-0.0.1-SNAPSHOT.jar app.jar

# Buka port 8080
EXPOSE 8080

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
