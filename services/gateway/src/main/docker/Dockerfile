FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY *.jar app.jar

# Exposition des ports
EXPOSE 8222

# Configuration des variables d'environnement
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Commande d'exécution
ENTRYPOINT ["java", "-jar", "/app/app.jar"]