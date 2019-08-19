gradle build
docker build -t gueka:spring-boot-docker-tutorial --build-arg JAR_FILE=./build/libs/spring-boot-docker-tutorial-0.0.1-SNAPSHOT.jar .
docker run -p 8080:8080 gueka:spring-boot-docker-tutorial