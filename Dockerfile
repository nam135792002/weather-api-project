FROM openjdk:17-jdk-slim

WORKDIR /app

COPY WeatherApiService/target/WeatherApiService-1.0.0.jar /app/WeatherApiService-1.0.0.jar

EXPOSE 8090

# Add the server.port parameter if you don't use Springboot default port 8080
CMD ["java", "-jar", "WeatherApiService-1.0.0.jar", "--server.port=8090"]
