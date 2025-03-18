# Stage 1: Build Vue App
FROM node:20.18.0-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci
COPY . .

RUN npx vue-tsc --noEmit  # Ensure TypeScript is checked
RUN npm run build

# Stage 2: Serve Vue App with Nginx
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

# Set Nginx config (optional)
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
