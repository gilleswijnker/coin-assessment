## Coin Search Engine

### Starting the application

#### When using Docker (recommended)
The application is available as a Docker Image. For purpose of this assessment also the database (with the data) is available as a Docker Image. Simply use the Dockercompose file to start up the application.
```
docker swarm init
docker stack deploy -c docker-compose.yml [name]
```


#### When first building from source

Before using the application the first time, the application has to be built.

Required (version numbers are the number the application was built with):
* maven (3.5.2)
* java version "1.8.0_152"
* Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
* Docker (17.12.1-ce)

Build the application with `mvn clean package`. This also creates a Docker container for the app. Now the app is ready to be run with Docker! See _When using Docker_ how to start the app.

### Stop the app ###
The application is now running in a Docker stack when the above steps have been followed. To stop the app, remove the Docker stack:
```
docker stack rm [name]
```
Use the `name` you have given the Stack when starting the application.

### Using the application
The application is available at port 8080 via any internet browser. 

### Content of the assessment
See _assessment.md_ for the assessment
