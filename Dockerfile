# Menggunakan image OpenJDK 17 sebagai base image
FROM openjdk:17-jdk-slim

# Menetapkan direktori kerja di dalam container
WORKDIR /app

# Menyalin pom.xml terlebih dahulu untuk memanfaatkan cache layer dari Docker
COPY pom.xml /app/

# Menginstal Maven
RUN apt-get update && apt-get install -y maven

# Menjalankan perintah untuk melakukan build aplikasi menggunakan Maven
RUN mvn clean package

# Menyalin file JAR yang telah dihasilkan dari build
COPY target/GeoCitySuggest-0.0.1-SNAPSHOT.jar /app/app.jar

# Mengekspos port 8080 untuk aplikasi
EXPOSE 8080

# Menentukan perintah yang akan dijalankan di container
ENTRYPOINT ["java", "-jar", "app.jar"]
