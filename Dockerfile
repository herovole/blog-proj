FROM node:18 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN pwd && ls -la && echo "Current directory:" && \
    npm run build && \
    pwd && ls -la && echo "After build:" && \
    ls -la ./build && \
    echo "Build directory contents:" && \
    tree ./build || { echo "Build failed"; exit 1; }

FROM nginx:latest
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
