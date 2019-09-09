FROM gradle:jdk8 as builder

WORKDIR /builder
COPY . .
RUN gradle build

FROM openjdk:8-jre-alpine
COPY --from=builder /builder/build/libs/sb2-rest-tutorial-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]