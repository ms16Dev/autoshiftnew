# Stage 1: Build Vue App
FROM node:20.18.0-alpine AS build

# Set working directory
WORKDIR /app

# Copy package files
COPY package.json package-lock.json ./

# Install all dependencies, including dev dependencies
RUN npm ci

# Copy the full app source
COPY . .

# Ensure vue-tsc is available
RUN npx vue-tsc --noEmit  # Type-check Vue files

# Build the Vue app
RUN npm run build

# Stage 2: Serve Vue App with Nginx
FROM nginx:alpine

# Copy built Vue files to Nginx web root
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
