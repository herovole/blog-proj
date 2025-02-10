FROM eclipse-temurin:17-jdk AS build
ENV TZ=Asia/Tokyo
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
WORKDIR /app
COPY docker/local/backend/blog-proj-1.0-SNAPSHOT.jar blog-proj.jar
COPY docker/local/backend/app_utility app_utility
EXPOSE 8080
RUN mkdir -p /var/log/blog && \
    chmod 755 /var/log/blog
ENTRYPOINT ["java", "-jar", "blog-proj.jar", "--spring.profiles.active=localdocker"]
