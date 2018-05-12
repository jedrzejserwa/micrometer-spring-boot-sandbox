FROM maven:3.5.3-jdk-8-alpine
WORKDIR /code
COPY . /code/
RUN mvn package

WORKDIR /app
RUN cp /code/target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]