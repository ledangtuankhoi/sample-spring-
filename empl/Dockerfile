# Sử dụng image Maven với JDK 17
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file cấu hình Maven vào container
COPY pom.xml ./

# # Tải xuống các dependencies để cache
# RUN mvn dependency:go-offline

# Copy source code vào container
COPY src ./src

# # Build ứng dụng
# RUN mvn clean package -DskipTests

# Chạy ứng dụng trực tiếp từ Maven để hỗ trợ hot reload
CMD ["mvn", "spring-boot:run"]
