FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY app.jar .
COPY data/ ./data/

ENTRYPOINT ["java", "-jar", "app.jar"]
