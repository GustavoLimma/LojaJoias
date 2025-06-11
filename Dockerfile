FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml primeiro (melhora cache do Docker)
COPY pom.xml .

# Copia o restante do código
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests

# Etapa final
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

COPY --from=build /app/target/LojaJoias-0.0.1-SNAPSHOT.jar LojaJoias.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "LojaJoias.jar"]

