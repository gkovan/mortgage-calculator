FROM eclipse-temurin:17
EXPOSE 8080
ARG JAR_FILE=target/mortgage-calculator-0.0.2-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]