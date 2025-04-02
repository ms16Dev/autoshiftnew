# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Add MongoDB's official repository and key
RUN apt update && apt install -y gnupg curl && \
    curl -fsSL https://www.mongodb.org/static/pgp/server-5.0.asc | tee /etc/apt/trusted.gpg.d/mongodb.asc && \
    echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/debian bullseye/mongodb-org/5.0 main" | tee /etc/apt/sources.list.d/mongodb-org-5.0.list

# Install MongoDB and Redis
RUN apt update && apt install -y mongodb-org redis-server

# Set working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/Autoshift-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the ports for MongoDB (27017), Redis (6379), and Spring Boot (8080)
EXPOSE 27017 6379 8080

# Create MongoDB data directory (but the data will be stored in volumes)
RUN mkdir -p /data/db /data/redis

# Start MongoDB & Redis & then run Spring Boot
CMD mkdir -p /data/redis /data/db && \
    redis-server --dir /data/redis && \
    mongod --fork --logpath /var/log/mongodb.log --dbpath /data/db --bind_ip_all && \
    java -jar app.jar