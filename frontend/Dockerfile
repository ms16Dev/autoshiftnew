# Stage 1: Build Vue App
FROM node:20.18.0-alpine AS build

# Set working directory
WORKDIR /app

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm ci --only=production

# Copy the entire app
COPY . .

# Build the Vue app (outputs to /app/dist)
RUN npm run build

# Stage 2: Serve Vue App with Nginx
FROM nginx:alpine

# Copy built Vue files to Nginx web root
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
