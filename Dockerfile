# use jre (https://hub.docker.com/r/library/openjdk/)
FROM openjdk:9-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY ./target/CoinSearchEngine-1.0.war /app

CMD ["/usr/bin/java", "-jar", "./CoinSearchEngine-1.0.war"]
