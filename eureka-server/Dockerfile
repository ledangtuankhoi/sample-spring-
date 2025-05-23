# # FROM maven:3.8.5-openjdk-8
# FROM maven:3.8.3-openjdk-17

# # Đặt thư mục làm việc
# WORKDIR /app

# # Copy toàn bộ mã nguồn vào container
# COPY . .

# # Cài đặt dependencies và chuẩn bị để phát triển
# # RUN mvn dependency:resolve
# CMD ["mvn", "clean install"]


# # Stage 1: Build ứng dụng
# FROM  maven:3.8.3-openjdk-17 


# # Đặt thư mục làm việc
# WORKDIR /app

# # Copy toàn bộ mã nguồn và pom.xml vào container
# COPY pom.xml .
# COPY src ./src

# # Cài đặt dependencies và build ứng dụng
# RUN mvn clean package -DskipTests
# # CMD ["mvn", "spring-boot:run"]


# # Stage 1: Build ứng dụng
# FROM maven:3.8.3-openjdk-17 AS builder
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# # Stage 2: Chạy ứng dụng
# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY --from=builder /app/target/eureka-server-demo-0.0.1-SNAPSHOT.jar app.jar
# CMD ["java", "-jar", "app.jar"]



# Sử dụng image Maven với JDK 17
FROM maven:3.8.3-openjdk-17 AS builder

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file cấu hình Maven và source code vào container
COPY pom.xml ./
COPY src ./src

# Lệnh này sẽ chỉ cài đặt dependencies để cache và tăng tốc build sau này
# RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Chạy ứng dụng trực tiếp từ Maven để hỗ trợ hot reload
CMD ["mvn", "spring-boot:run"]
