FROM public.ecr.aws/docker/library/maven:3.8.7-eclipse-temurin-17 AS build
ENV TZ=Asia/Tokyo
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
WORKDIR /app
COPY . .
RUN mvn package -DskipTests -Plocaldocker

FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS exe
ENV TZ=Asia/Tokyo
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
WORKDIR /app
COPY --from=build /app/target/*.jar blog-proj.jar
COPY build/local/backend/app_utility/ app_utility/
EXPOSE 8080
RUN mkdir -p /var/log/blog && \
    chmod 755 /var/log/blog
ENTRYPOINT ["java", "-jar", "blog-proj.jar", "--spring.profiles.active=localdocker"]
