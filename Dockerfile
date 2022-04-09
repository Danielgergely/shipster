FROM maven:3.8-openjdk-17-slim as base
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src

FROM base as development
EXPOSE 8001
EXPOSE 8080
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8001'"]

FROM base as build
RUN mvn package
RUN java -Djarmode=layertools -jar target/application.jar extract

FROM openjdk:17-alpine as production
EXPOSE 8080
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build app/dependencies/ ./
COPY --from=build app/snapshot-dependencies/ ./
COPY --from=build app/spring-boot-loader/ ./
COPY --from=build app/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
