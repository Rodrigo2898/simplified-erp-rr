FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
LABEL "org.opencontainers.image.authors"="rodfeitosa28"
WORKDIR /app

COPY --from=builder /app/target/produto-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]