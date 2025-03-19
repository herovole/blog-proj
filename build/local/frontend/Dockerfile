FROM node:18 AS build
ENV TZ=Asia/Tokyo
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN echo "Current directory:" && pwd && ls -la && \
    npm run build && \
    echo "Build directory contents:" && pwd && ls -la ./src/main/resources/static/dist

FROM nginx:latest
ENV TZ=Asia/Tokyo
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
COPY --from=build /app/src/main/resources/static /var/www/html
COPY --from=build /app/src/main/resources/templates /var/www/html
COPY --from=build /app/src/main/resources/favicon.ico /var/www/html/favicon.ico
COPY build/local/frontend/nginx/nginx.conf /etc/nginx/nginx.conf
COPY build/local/frontend/nginx/conf.d /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


