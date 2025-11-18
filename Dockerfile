# use java 21 runtime
FROM eclipse-temurin:21-jre-alpine

# set working directory
WORKDIR /app

# copy the jar file to working directory
COPY target/*.jar app.jar

# expose port 8080
EXPOSE 8080

# run the jar file
CMD ["java", "-jar", "app.jar"]