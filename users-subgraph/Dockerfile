FROM openjdk:17
EXPOSE 8082

ARG JAR_FILE=build/libs/users.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
