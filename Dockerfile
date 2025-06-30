FROM openjdk:11-jre-slim

WORKDIR /app

COPY WeatherApiService/target/hello-world-1.0-SNAPSHOT.jar /app/hello-world.jar

EXPOSE 8090

# Add the server.port parameter if you don't use Springboot default port 8080
CMD ["java", "-jar", "WeatherApiService-1.0.0.jar", "--server.port=8090"]
