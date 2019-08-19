gradle build
docker build -t gueka:sb2-rest-tutorial --build-arg JAR_FILE=./build/libs/rules-0.0.1-SNAPSHOT.jar .
docker run -p 8080:8080 gueka:sb2-rest-tutorial