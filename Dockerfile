FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY app.jar .
COPY lib/ ./lib/
COPY scripts/ ./scripts/
COPY data/ ./data/

RUN chmod +x ./scripts/*.sh

RUN ./scripts/init-db.sh

ENTRYPOINT ["java", "-jar", "app.jar"]
