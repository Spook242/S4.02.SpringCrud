# -------------------------------------------------------------------
# ETAPA 1: BUILD (Compilació)
# CAMBIO IMPORTANTE: Usamos temurin-21 (Java 21) en lugar de 17
# -------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# -------------------------------------------------------------------
# ETAPA 2: RUNTIME (Producció)
# CAMBIO IMPORTANTE: Usamos temurin:21-jre (Java 21)
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]