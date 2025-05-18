# Stage 1: Build Vue App
FROM node:20 AS build
WORKDIR /app

# Copy and install dependencies
COPY package.json package-lock.json ./
RUN npm ci

# Copy the rest of the app
COPY . .

# Type check (optional but useful)
RUN npx vue-tsc --noEmit

# Build the app
RUN npm run build

# Stage 2: Serve using Nginx
FROM nginx:stable-alpine

# Copy built files to Nginx public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Optional: custom nginx config
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
