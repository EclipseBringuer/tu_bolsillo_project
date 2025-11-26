# ------------------------------------
# STAGE 1: COMPILACIÓN (BUILD) con JDK y Maven
# ------------------------------------
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copia los archivos de configuración y código fuente
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# Copia del código fuente
COPY src ./src

# Permisos al wrapper
RUN chmod +x mvnw

# Compila el proyecto y genera el JAR
RUN ./mvnw clean package -DskipTests

# ------------------------------------
# STAGE 2: SERVICIO (PRODUCTION) con JRE
# ------------------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia el JAR generado en la etapa de compilación
COPY --from=build /app/target/tubolsillo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+ExitOnOutOfMemoryError", "-jar", "app.jar"]